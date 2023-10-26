import { ref } from "vue";
import { defineStore } from "pinia";
import type { RecentProducts } from "@/interface/productInterface";

export const useProductStore = defineStore("product", {
  id: "product",
  state: () => ({
    recentProducts: [] as RecentProducts[],
  }),
  actions: {
    setRecentProducts(id: string, thumbnail: string) {
      if (this.recentProducts.length === 50) {
        this.recentProducts.shift();
        this.recentProducts.push({ id, thumbnail });
      } else {
        this.recentProducts.push({ id, thumbnail });
        this.recentProducts = this.recentProducts.filter(
          (item, index, self) =>
            index === self.findIndex((t) => t.id === item.id)
        );
      }
    },
  },
  persist: {
    enabled: true,
    strategies: [
      {
        name: "localStorage",
        storage: window.localStorage,
        sync: ["recentProducts"],
      },
    ],
  },
});
