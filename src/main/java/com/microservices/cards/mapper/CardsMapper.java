package com.microservices.cards.mapper;

import com.microservices.cards.model.dto.CardsDto;
import com.microservices.cards.model.entity.Cards;

public class CardsMapper {


    public static CardsDto mapToCardsDto (Cards cards , CardsDto cardsDto){

        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        return cardsDto;
    }


    public static Cards mapToCards (CardsDto cardsDto , Cards cards){
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setCardType(cardsDto.getCardType());
        cards.setMobileNumber(cardsDto.getMobileNumber());
        return cards;
    }
}
