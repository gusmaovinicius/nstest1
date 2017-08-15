package br.com.vgusmao.service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import br.com.vgusmao.model.Campaign;
import br.com.vgusmao.repository.CampaignRepository;
import reactor.core.publisher.Mono;

@Service
public class CampaignHandler {
	
	private final CampaignRepository campaignRepository;
	
	@Autowired
	public CampaignHandler(CampaignRepository campaignRepository){
		this.campaignRepository = campaignRepository;
	}
	
	public Mono<ServerResponse> listAll(ServerRequest serverRequest){
			
		return ServerResponse.ok().body(this.campaignRepository.listCurrentCamapaigns(new Date()), Campaign.class);
	}
	
	public Mono<ServerResponse> save(ServerRequest serverRequest){
		Mono<Campaign> campaignMono = serverRequest.bodyToMono(Campaign.class);		
		
		Mono<Campaign> returnCampaign = campaignMono.flatMap(c->{			
			return updateCampaigns(c);			
		});
		
		return ServerResponse.ok().body(returnCampaign, Campaign.class);     
    }
	
	private Mono<Campaign> updateCampaigns(Campaign c) {
		
		Date endDate = c.getEndDate();
		Iterator <Campaign> listItera = this.campaignRepository.findAll()
		.sort(Comparator.comparing(c2->endDate))
		.toIterable().iterator();
		int plusDate = 0;
		while(listItera.hasNext())
		{
			plusDate++;
			Campaign camp = listItera.next();
			Date currentDate = camp.getEndDate();
			
			if(currentDate.getDay() == endDate.getDay() 
					&& currentDate.getMonth() == endDate.getMonth()
					&& currentDate.getYear() == endDate.getYear())
			{				
				Calendar cal = Calendar.getInstance(); 
				cal.setTime(currentDate); 
				cal.add(Calendar.DATE, plusDate);
				currentDate = cal.getTime();
			
				camp.setEndDate(currentDate);
				
				this.campaignRepository.save(camp).block();
			}
			
		}		
		
		return this.campaignRepository.insert(c);		
	}	

	public Mono<ServerResponse> update(ServerRequest serverRequest){		
        return ServerResponse.ok().body(this.campaignRepository.save(serverRequest.bodyToMono(Campaign.class).block()), Campaign.class);
    }
	
	public Mono<ServerResponse> delete(ServerRequest serverRequest){	
		System.out.println(serverRequest.pathVariable("id"));
        return ServerResponse.ok().body(this.campaignRepository.deleteById(serverRequest.pathVariable("id")), Void.class);
    }	
}
