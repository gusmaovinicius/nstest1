package br.com.vgusmao.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import br.com.vgusmao.model.Campaign;
import br.com.vgusmao.model.Fan;
import br.com.vgusmao.model.FanSubscription;
import br.com.vgusmao.model.Message;
import br.com.vgusmao.repository.FanRepository;
import br.com.vgusmao.repository.SubscriptionRepository;
import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;;

@Service
public class FanHandler {

private final FanRepository fanRepository;
private final SubscriptionRepository subscriptionRepository;
	
	@Autowired
	public FanHandler(FanRepository fanRepository, SubscriptionRepository subscriptionRepository){
		this.fanRepository = fanRepository;
		this.subscriptionRepository = subscriptionRepository;
	}
	
	public Mono<ServerResponse> create(ServerRequest serverRequest){
		Mono<Fan> fanMono = serverRequest.bodyToMono(Fan.class);
		
		return fanMono.flatMap(c->{	
			Message message;
			Iterator<Fan> existFanIterator = this.fanRepository.findById(c.getEmail()).flux().toIterable().iterator();
			
			if(!existFanIterator.hasNext())
			{				
				this.fanRepository.insert(c).block();
				subscribeNewCampaigns(c);		
				message = new Message("Cadastro efetuado com sucesso!");
				return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(message));
			}
			else
			{
				message = new Message("Já existe um cadastro com este endereço de email");
				return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(message));
			}
			
		});		
	}
	
	public Mono<ServerResponse> validateEmail(ServerRequest serverRequest){
		Mono<Fan> fanMono = serverRequest.bodyToMono(Fan.class);
		
		try
		{
			Mono<Fan> fan = fanMono.flatMap(c->{				
				return this.fanRepository.findById(c.getEmail());									
			});
			
			return fan.flatMap(f->{	
				Message errorMessage = new Message("Falha ao recuperar campanhas");
				try{
					Iterable<FanSubscription> iterable = this.subscriptionRepository.listFanSubscriptions(f.getEmail()).toIterable();
					List<FanSubscription> fanCampaignLst = new ArrayList<FanSubscription>();
					iterable.forEach(fanCampaignLst::add);
					
					if(fanCampaignLst != null && fanCampaignLst.size() > 0)
						return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(fanCampaignLst));
					else
					{
						List<Campaign> activeCampaings = getCampaignsToSubscribe(f);
						subscribeNewCampaigns(f);
						
						if(activeCampaings != null && activeCampaings.size() > 0)
							return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(activeCampaings));
						else
							return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(errorMessage));
					}
				}catch(Exception ex1)
				{					
					return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(errorMessage));
				}
			});
		
		}catch(Exception ex2)
		{
			Message message = new Message("Falha ao recuperar informações do servidor");
			return ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(message));
		}			
	}

	public Mono<ServerResponse> listAll(ServerRequest serverRequest){
		
		return ServerResponse.ok().body(this.fanRepository.findAll(), Fan.class);
	}
	
	public Mono<ServerResponse> listFanSubscriptions(ServerRequest serverRequest){
		Mono<Fan> fanMono = serverRequest.bodyToMono(Fan.class);
		
		return fanMono.flatMap(f->{
			return ServerResponse.ok().body(this.subscriptionRepository.listFanSubscriptions(f.getEmail()), FanSubscription.class);
		});		
	}
	
	private List<Campaign> getActiveCampaigns(){
		try{
			RestTemplate rest  = new RestTemplate();
			ResponseEntity<Campaign[]> responseEntity  = rest.getForEntity("http://localhost:9000/api/campaign/", Campaign[].class);
			Campaign[] campaigns = responseEntity.getBody();
			
			if(campaigns != null && campaigns.length > 0)
				return Arrays.asList(campaigns);
			else
				return null;
		}catch(Exception ex1)
		{					
			return null;
		}
	}
	
	private List<Campaign> getCampaignsToSubscribe(Fan fan){
		List<Campaign> campaignsToSubscribe = new ArrayList<Campaign>();
		List<Campaign> activeCampaigns = getActiveCampaigns();
		if(activeCampaigns != null && activeCampaigns.size() > 0)
		{
			Iterable<FanSubscription> iterable = this.subscriptionRepository.listFanSubscriptions(fan.getEmail()).toIterable();
								
			List<String> subscriptionsLst = new ArrayList<String>();
			iterable.forEach(fcl->{
				subscriptionsLst.add(fcl.getCampaign());
			});
			
			campaignsToSubscribe =  activeCampaigns.stream().filter(a->a.getHeartTeam().equals(fan.getHeartTeam())
					&& !subscriptionsLst.contains(a.getName())).collect(Collectors.toList());
						 
		}
		return campaignsToSubscribe;
	}
	
	private void subscribeNewCampaigns(Fan fan){
		List<Campaign> campaignsToSubscribe = getCampaignsToSubscribe(fan);
		if(campaignsToSubscribe != null && campaignsToSubscribe.size() > 0)
		{
			campaignsToSubscribe.forEach(ca-> {				
				FanSubscription fs = new FanSubscription(fan.getEmail(), ca.getName(), ca.getStartDate(), ca.getEndDate());
				this.subscriptionRepository.insert(fs).block();
			});
		}
	}	
}
