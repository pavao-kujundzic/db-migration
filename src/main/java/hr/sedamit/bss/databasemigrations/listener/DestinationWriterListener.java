package hr.sedamit.bss.databasemigrations.listener;


import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DestinationWriterListener implements ItemWriteListener {
    @Override
    public void beforeWrite(List items) {
        System.out.println("ItemWriteListener - beforeWrite");
    }

    @Override
    public void afterWrite(List items) {
        System.out.println("ItemWriteListener - afterWrite");
    }

    @Override
    public void onWriteError(Exception exception, List items) {
        System.out.println("ItemWriteListener - onWriteError");
    }
}
