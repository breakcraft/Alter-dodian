package gg.rsmod.plugins.content.area.edgeville

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency
import gg.rsmod.game.model.shop.PurchasePolicy


create_shop("General Store", CoinCurrency(), purchasePolicy = PurchasePolicy.BUY_TRADEABLES, stockType = StockType.NORMAL) {
            items[0] = ShopItem(Items.TINDERBOX, 1)
            items[1] = ShopItem(Items.CHISEL, 2, 1, 0, 1)
            items[2] = ShopItem(Items.HAMMER, 5)
            items[3] = ShopItem(Items.SPADE, 1)
        }