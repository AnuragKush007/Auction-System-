package sdm.engine.CutomSDMClasses;

import sdm.engine.exceptions.LocationAlreadyInUseException;
import sdm.engine.exceptions.LocationNotInRangeException;
import sdm.engine.exceptions.ObjectAlreadyExistException;
import sdm.engine.exceptions.ObjectNotExistException;
import sdm.engine.exceptions.UnavailableItemsException;
import sdm.engine.DefaultSDMClasses.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class XmlLoader {

    private static final String JAXB_XML_PACKAGE_NAME = "sdm.engine.DefaultSDMClasses";
    private static final String FILE_PATH_EXTENSION = ".xml";

    public XmlLoader() {}

    public synchronized void loadFile(SuperDuperMarket superDuperMarket, InputStream fileContent,
                                      StoreOwner owner) throws JAXBException, FileNotFoundException { //TODO: check file type is .xml in UI
        SuperDuperMarketDescriptor superDuperMarketDescriptor = deserializeFrom(fileContent);
        Zone zone = loadZone(superDuperMarketDescriptor, superDuperMarket, owner);
        loadItems(superDuperMarketDescriptor, zone);
        loadStores(superDuperMarketDescriptor, zone, owner);
        Set<Item> unavailableItems = zone.getItemsThatAreNotSoldInAnyStore();
        if(!(unavailableItems.isEmpty()))
            throw new UnavailableItemsException(unavailableItems);
        try {
            superDuperMarket.addZone(zone);
        } catch (ObjectAlreadyExistException e){
            throw new IllegalArgumentException("Can't add Zone, because " + e.getMessage());
        }
    }

    private static Zone loadZone(SuperDuperMarketDescriptor superDuperMarketDescriptor, SuperDuperMarket superDuperMarket, StoreOwner owner) {
        SuperDuperMarketDescriptor.SDMZone sdmZone = superDuperMarketDescriptor.getSDMZone();
        Zone zone = new Zone(sdmZone, owner);
        return zone;
    }

    private static void loadItems(SuperDuperMarketDescriptor superDuperMarketDescriptor, Zone zone) {
        List<SDMItem> sdmItems = superDuperMarketDescriptor.getSDMItems().getSDMItem();
        for (SDMItem sdmItem : sdmItems) {
            Item item = new Item(sdmItem);
            try {
                zone.addItem(item);
            } catch (ObjectAlreadyExistException e){
                throw new IllegalArgumentException("Can't add item, because " + e.getMessage());
            }
        }
    }

    private void loadStores(SuperDuperMarketDescriptor superDuperMarketDescriptor, Zone zone, StoreOwner owner) {
        List<SDMStore> sdmStores = superDuperMarketDescriptor.getSDMStores().getSDMStore();
        for (SDMStore sdmStore : sdmStores) {
            Store store = new Store(sdmStore, owner);
            try {
                zone.addStoreFromXml(store);
            } catch (LocationAlreadyInUseException e){
                throw new IllegalArgumentException("Can't add the store: " + store.getName() + ", because " + e.getMessage());
            } catch (LocationNotInRangeException e){
                throw new IllegalArgumentException("Can't add the store: " + store.getName() + ", because " + e.getMessage());
            }catch (ObjectAlreadyExistException e){
                throw new IllegalArgumentException("Can't add the store: " + store.getName() + ", because " + e.getMessage());
            }
            loadItemsToStore(store, sdmStore, zone);
            loadDiscountsToStore(store, sdmStore, zone);
        }
    }

    private void loadItemsToStore(Store store, SDMStore sdmStore, Zone zone) {
        SDMPrices sdmPrices = sdmStore.getSDMPrices();
        List<SDMSell> sdmSells = sdmPrices.getSDMSell();

        for (SDMSell sdmSell : sdmSells) {
            int itemId = sdmSell.getItemId();
            double itemPrice = sdmSell.getPrice();
            try {
                Item item = zone.getItemById(itemId);
                store.addItem(item, itemPrice);
                item.addStoreToAvailableIn(store);
            } catch (ObjectNotExistException e) {
                throw new IllegalArgumentException("Can't add item to the store: " + store.getName() + ", because " + e.getMessage());
            } catch (ObjectAlreadyExistException e){
                throw new IllegalArgumentException("Can't add a item twice to the same store: " + store.getName() + ". " + e.getMessage());
            }
        }
    }

    private void loadDiscountsToStore(Store store, SDMStore sdmStore, Zone zone) {
        SDMDiscounts sdmDiscountsContainer = sdmStore.getSDMDiscounts();
        if (sdmDiscountsContainer != null) {
            List<SDMDiscount> sdmDiscounts = sdmDiscountsContainer.getSDMDiscount();

            for (SDMDiscount sdmDiscount : sdmDiscounts) {
                Discount discount = new Discount(sdmDiscount);
                List<SDMOffer> sdmOffers = sdmDiscount.getThenYouGet().getSDMOffer();
                try {
                    for (SDMOffer sdmOffer : sdmOffers) {
                        Offer offer = new Offer(sdmOffer);
                        discount.addOffer(offer);
                    }
                    zone.addDiscountToStore(store.getID(), discount);
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Can't add discount: " + discount.getName() + ", because " + e.getMessage());
                }
            }
        }
    }

    private SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }
}
