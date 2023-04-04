package com.example.sockswarehouseautomation.service;

import com.example.sockswarehouseautomation.dto.UpdateQuantityDTO;
import com.example.sockswarehouseautomation.entity.Socks;
import com.example.sockswarehouseautomation.exception.NotEnoughSocksException;
import com.example.sockswarehouseautomation.exception.OperationDoesNotExist;
import com.example.sockswarehouseautomation.mapper.SocksMapper;
import com.example.sockswarehouseautomation.repository.SocksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocksService {
    private final SocksRepository repository;

    /**
     * Method for search the total number of socks pairs in the storage
     * that meet the condition of chosen operation.
     *
     * @param color      - socks color.
     * @param operation  - search term name.
     * @param cottonPart - percentage of cotton.
     * @return quantity of socks.
     */
    public Integer getCount(String color, String operation, int cottonPart) {
        log.debug("method getCount had been started");
        Integer quantity;
        switch (operation) {
            case "equal": {
                quantity = repository.findQuantityByColorAndCottonPart(color, cottonPart)
                        .orElse(0);
                break;
            }
            case "lessThan": {
                quantity = repository.findQuantityByColorAndCottonPartLessThan(color, cottonPart)
                        .orElse(0);
                break;
            }
            case "moreThan": {
                quantity = repository.findQuantityByColorAndCottonPartGreaterThan(color, cottonPart)
                        .orElse(0);
                break;
            }
            default:
                log.error("there is no operation named " + operation);
                throw new OperationDoesNotExist();
        }
        log.debug("method getCount had been ended");
        return quantity;
    }

    /**
     * Method for writing new information about socks to the database
     * or updating the number of socks if the information already exist.
     *
     * @param update received JSON with socks information.
     */
    public void incomeSocks(UpdateQuantityDTO update) {
        log.debug("method incomeSocks had been started");
        Socks socks = repository.findByColorAndCottonPart(update.getColor(), update.getCottonPart())
                .orElse(null);

        if (socks == null) {
            socks = SocksMapper.INSTANCE.updateToSocks(update);
        } else {
            socks.setQuantity(socks.getQuantity() + update.getQuantity());
        }

        repository.save(socks);
        log.debug("method incomeSocks had been ended");
    }

    /**
     * Method for update the number of existing socks.
     * If after reduction the number of socks is 0, than the delete method if called.
     *
     * @param update received JSON with socks information.
     */
    public void outcomeSocks(UpdateQuantityDTO update) {
        log.debug("method outcomeSocks had been started");
        Socks socks = repository.findByColorAndCottonPart(update.getColor(), update.getCottonPart())
                .orElse(null);

        if (socks == null || socks.getQuantity() < update.getQuantity()) {
            log.error("not enough socks to outcome");
            throw new NotEnoughSocksException();
        }

        socks.setQuantity(socks.getQuantity() - update.getQuantity());

        if (socks.getQuantity() == 0) {
            deleteSocks(socks.getId());
        } else {
            repository.save(socks);
        }
        log.debug("method outcomeSocks had been ended");
    }

    /**
     * Method for deleting socks information from DB
     *
     * @param id - id of socks type
     */
    public void deleteSocks(Long id) {
        log.debug("method deleteSocks had been started");
        repository.deleteById(id);
        log.debug("method deleteSocks had been ended");
    }
}
