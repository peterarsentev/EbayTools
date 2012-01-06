package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.ItemProperties;

import java.util.List;

public interface ItemPropetiesDAO extends CrUD<ItemProperties> {
    public List<ItemProperties> getItemPropetiesForItemId(Long itemId);
}
