package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class itemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA", 10000, 10);
        //when

        Item savedItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(item);
    }

    @Test
    void findAll(){
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> findall = itemRepository.findAll();
        Assertions.assertThat(findall.size()).isEqualTo(2);
    }

    @Test
    void updateItem(){
        Item item1 = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item1);
        Long itemId = savedItem.getId();

        Item updateParam = new Item("item2", 20000, 30);

        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
    }


}