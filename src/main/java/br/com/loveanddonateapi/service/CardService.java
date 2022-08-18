package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Card;
import br.com.loveanddonateapi.repository.CardRepository;
import br.com.loveanddonateapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService implements BaseService< CardDTO, Card> {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Card createOrUpdate(CardDTO cardDTO) {
        Card card = cardDTO.asEntity(cardDTO);
        //precisa pegar o usuario logado
        return cardRepository.save(card);
    }

    @Override
    public CardDTO getById(Long id) {
        return cardRepository.findById(id).map(card -> new CardDTO(card))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cartão com identificador [%d] não encontrado.", id)));
    }

    @Override
    public List<CardDTO> getAll() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cartão com identificador [%d] não encontrado.", id)));
        cardRepository.delete(card);
    }
}
