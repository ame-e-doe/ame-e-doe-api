package br.com.loveanddonateapi.service;

import br.com.loveanddonateapi.dto.CardDTO;
import br.com.loveanddonateapi.exception.EntityExistValidateException;
import br.com.loveanddonateapi.exception.EntityNotFoundException;
import br.com.loveanddonateapi.models.Card;
import br.com.loveanddonateapi.models.User;
import br.com.loveanddonateapi.repository.CardRepository;
import br.com.loveanddonateapi.configuration.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService implements BaseService<CardDTO> {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public CardDTO create(CardDTO cardDTO, String token) {
        Long userId = Long.parseLong(jwtUtils.getUserFromJwtToken(token));
        cardExist(cardDTO.getCardNumber(), userId);
        Card card = cardDTO.asEntity(cardDTO);
        User user = userService.getById(userId);
        card.setUser(user);
        return new CardDTO(cardRepository.save(card));
    }

    @Override
    public CardDTO getById(Long id) {
        return cardRepository.findById(id).map(card -> new CardDTO(card))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cartão com identificador [%d] não encontrado.", id)));
    }

    @Override
    public List<CardDTO> getAll(String token) {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cartão com identificador [%d] não encontrado.", id)));
        cardRepository.delete(card);
    }

    private void cardExist(String cardNumber, Long user) {
        if (cardRepository.findCardByCardNumberAndUser(cardNumber, user).isPresent()) {
            throw new EntityExistValidateException("Cartão já cadastrado.");
        }
    }
}
