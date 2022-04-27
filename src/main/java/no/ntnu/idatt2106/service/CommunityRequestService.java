package no.ntnu.idatt2106.service;

import no.ntnu.idatt2106.repository.CommunityRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class CommunityRequestService {
    private final CommunityRequestRepository communityRequestRepository;

    public CommunityRequestService(CommunityRequestRepository communityRequestRepository) {
        this.communityRequestRepository = communityRequestRepository;
    }


}
