import { ref, watchEffect } from "vue";
import { defineStore } from "pinia";
import type { RecentProducts } from "@/interface/productInterface";

export const useProductStore = defineStore("product", () => {
  const recentProducts = ref<RecentProducts[]>([]);
  const setRecentProducts = (id: number, thumbnail: string) => {
    if (recentProducts.value.length === 50) {
      recentProducts.value.shift();
      recentProducts.value.push({ id, thumbnail });
    } else {
      recentProducts.value.push({ id, thumbnail });
      recentProducts.value = recentProducts.value.filter(
        (item, index, self) => index === self.findIndex((t) => t.id === item.id)
      );
    }
  };

  if (localStorage.getItem("product")) {
    recentProducts.value = JSON.parse(<any>localStorage.getItem("product"));
  }

  watchEffect(() => {
    localStorage.setItem("product", JSON.stringify(recentProducts.value));
  }, <any>recentProducts.value);

  return { recentProducts, setRecentProducts };
});
