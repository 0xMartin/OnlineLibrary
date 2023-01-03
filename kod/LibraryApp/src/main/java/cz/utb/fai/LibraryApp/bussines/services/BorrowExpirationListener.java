package cz.utb.fai.LibraryApp.bussines.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import cz.utb.fai.LibraryApp.model.Book;

public class BorrowExpirationListener extends AbstractMongoEventListener<Book> {

    private static final Logger logger = LoggerFactory.getLogger(BorrowExpirationListener.class);

    @Override
    public void onAfterSave(AfterSaveEvent<Book> event) {
        logger.debug("onAfterSave({}, {})", event.getSource(), event.getDocument());
    }

}
