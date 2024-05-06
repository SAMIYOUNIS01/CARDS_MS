package com.microservices.cards.service.impl;

import com.microservices.cards.constants.CardsConstants;
import com.microservices.cards.exception.CardsAlreadyExistsException;
import com.microservices.cards.exception.ResourcesNotFoundException;
import com.microservices.cards.mapper.CardsMapper;
import com.microservices.cards.model.dto.CardsDto;
import com.microservices.cards.model.entity.Cards;
import com.microservices.cards.repository.CardsRepository;
import com.microservices.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    CardsRepository cardsRepository;
    @Override
    public void createCardByMobileNumber(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent()){
            throw new CardsAlreadyExistsException("Cards Already exist with given mobile number "+mobileNumber);
        }

        cardsRepository.save(createNewCard(mobileNumber));
    }

    @Override
    public CardsDto fetchCardsByMobileNumber(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourcesNotFoundException("Cards" , "mobileNumber" , mobileNumber)
        );

        return CardsMapper.mapToCardsDto(cards , new CardsDto());
    }

    @Override
    public boolean updateCardsByMobileNumber(CardsDto cardsDto) {
        boolean isUpdated = false;
        Cards cards = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                () -> new ResourcesNotFoundException("Cards" , "mobileNumber" , cardsDto.getMobileNumber())
        );

        cardsRepository.save(CardsMapper.mapToCards(cardsDto , cards));
        isUpdated = true;
        return isUpdated;

    }

    @Override
    public boolean deleteCardsByMobileNumber(String mobileNumber) {
        boolean isDeleted = false;
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourcesNotFoundException("Cards" , "mobileNumber" , mobileNumber)
        );
        cardsRepository.delete(cards);
        isDeleted = true;
        return isDeleted;

    }

    private Cards createNewCard(String mobileNumber) {
        Cards cards = new Cards();
        cards.setMobileNumber(mobileNumber);
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        return cards;
    }
}
