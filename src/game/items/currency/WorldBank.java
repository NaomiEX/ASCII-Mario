package game.items.currency;

import engine.actors.Actor;
import engine.items.Item;

import java.util.*;

public class WorldBank {
    private final Map<Actor, Set<Coin>> actorBank;
    private final ArrayList<Coin> worldCoins;
    private static WorldBank instance;

    private WorldBank() {
        this.actorBank = new HashMap<>();
        this.worldCoins = new ArrayList<>();
    }

    public static WorldBank getInstance() {
        if (instance == null) {
            instance = new WorldBank();
        }
        return instance;
    }

    public void addCoin(Actor actor, Coin coin) {
        if(this.hasActor(actor)) {
            this.addCoinToActor(actor, coin);
        } else {
            Set<Coin> actorWallet = new HashSet<>();
            actorWallet.add(coin);
            this.actorBank.put(actor, actorWallet);
        }
    }

    // add coin to world
    public void addCoin(Coin coin) {
        this.worldCoins.add(coin);
    }

    public Coin getCorrespondingCoin(Item item) {
        for (Coin coin: this.worldCoins) {
            if (item == coin) {
                return coin;
            }
        }
        return null;
    }

    public boolean isValidCurrency(Item item) {
        Coin result = this.getCorrespondingCoin(item);
        return result != null;
    }


    public void removeCoin(Coin coin) {
        this.worldCoins.remove(coin);
    }

    //TODO: add checks
    public void spendCoin(Actor actor, Coin coin) {
        Coin currentCoinBalance = this.getBalance(actor, coin.getCoinType());
        currentCoinBalance.setValue(currentCoinBalance.getValue() - coin.getValue());
    }

    private void addCoinToActor(Actor actor, Coin coin) {
        Set<Coin> actorWallet = this.actorBank.get(actor);
        Coin thisCoin = this.getCoin(coin.getCoinType(), actorWallet);
        if (thisCoin == null) {
            actorWallet.add(coin);
        } else {
            thisCoin.setValue(thisCoin.getValue() + coin.getValue());
        }
    }

    public Coin getBalance(Actor actor, String coinType) {
        Coin retCoin = null;
        if (this.hasActor(actor)) {
            Set<Coin> actorWallet = this.actorBank.get(actor);
            retCoin = this.getCoin(coinType, actorWallet);
        }
        return retCoin;
    }

    public boolean hasActor(Actor actor) {
        return this.actorBank.containsKey(actor);
    }

    public Coin getCoin(String coinType, Set<Coin> coinSet) {
        for (Coin coin : coinSet) {
            if (Objects.equals(coin.getCoinType(), coinType)) {
                return coin;
            }
        }
        return null;
    }

//    @Override
//    public void resetInstance(Location location) {
//        GameMap map = location.map();
//        for (int x : map.getXRange()) {
//            for (int y : map.getYRange()) {
//                Location locationHere = map.at(x, y);
//                if (locationHere.getItems() != null) {
//                    List<Item> itemsHere = new ArrayList<>(locationHere.getItems());
//                    for (Item itemHere : itemsHere) {
//                        if (this.isValidCurrency(itemHere)) {
//                            locationHere.removeItem(itemHere);
//                        }
//                    }
//                }
//            }
//        }
//    }
}
