package org.route.repository;

import org.route.model.orderPromotion.Promotion;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PromotionRepository {
   private final Map<String, Promotion> promotions=new HashMap<>();

   public List<Promotion> findAll() {
       return promotions.values().stream().toList();
   }

   public Optional<Promotion> getPromotion(String promotionId) {
        return Optional.ofNullable(promotions.get(promotionId.toLowerCase()));
   }

   public void addPromotion(Promotion promotion) {
       promotions.put(promotion.getPromotionCode().toLowerCase(),promotion);
   }


}
