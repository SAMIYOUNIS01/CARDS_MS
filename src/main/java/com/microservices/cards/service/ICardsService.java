package com.microservices.cards.service;

import com.microservices.cards.model.dto.CardsDto;

public interface ICardsService {


    void createCardByMobileNumber (String mobileNumber);


    CardsDto fetchCardsByMobileNumber (String mobileNumber);


    boolean updateCardsByMobileNumber (CardsDto cardsDto);

    boolean deleteCardsByMobileNumber(String mobileNumber);


}
