import { defineStore } from "pinia";
import jwtInterceptor from "../shared/jwtInterceptor";
import { VITE_APP_API_URL } from "./helper";
import { parseItem, parseDeleteResponse } from "../shared/data.service";

const newSampleDataCategory = {
  id: undefined,
  name: "",
  version: 0,
  createdAt: new Date().toISOString(),
  modifiedAt: new Date().toISOString(),
};

export const useSampleDataCategoryStore = defineStore(
  "SampleDataCategoryStore",
  {
    state() {
      return {
        sampleDataCategories: [],
        selectedSampleDataCategory: {},
      };
    },
    getters: {
      doubleCount: (state) => state.counter * 2,
    },
    actions: {
      addSampleDataCategoryAction(sampleDataCategory) {
        return jwtInterceptor
          .post(
            `${VITE_APP_API_URL}/api/sample-data-categories`,
            sampleDataCategory
          )
          .then((response) => {
            const addedSampleDataCategory = parseItem(response, 201);
            this.sampleDataCategories.unshift(addedSampleDataCategory); // mutable addition
            this.selectedSampleDataCategory = addedSampleDataCategory;
          })
          .catch((error) => console.error(error));
      },
      deleteSampleDataCategoryAction(sampleDataCategory) {
        return jwtInterceptor
          .delete(
            `${VITE_APP_API_URL}/api/sample-data-categories/${sampleDataCategory.name}`
          )
          .then((response) => {
            parseDeleteResponse(response, 200);
            this.sampleDataCategories = [
              ...this.sampleDataCategories.filter(
                (p) => p.name !== sampleDataCategory.name
              ),
            ];
            this.selectedSampleDataCategory = { ...newSampleDataCategory };
          })
          .catch((error) => console.error(error));
      },
      getSampleDataCategoriesAction(categoryName) {
        let url = VITE_APP_API_URL + "/api/sample-data-categories";
        if (categoryName && categoryName !== "") {
          url = url + "?categoryName=" + categoryName;
        }
        return jwtInterceptor
          .get(url)
          .then((response) => {
            this.sampleDataCategories = response.data.items;
            this.selectedSampleDataCategory = { ...newSampleDataCategory };
          })
          .catch((error) => {
            console.log(error);
          });
      },
      updateSampleDataCategoryAction(sampleDataCategory) {
        return jwtInterceptor
          .put(
            `${VITE_APP_API_URL}/api/sample-data-categories/${sampleDataCategory.id}`,
            sampleDataCategory
          )
          .then((response) => {
            const updatedSampleDataCategory = parseItem(response, 200);
            const index = this.sampleDataCategories.findIndex(
              (h) => h.id === updatedSampleDataCategory.id
            );
            this.sampleDataCategories.splice(
              index,
              1,
              updatedSampleDataCategory
            );
            this.sampleDataCategories = [...this.sampleDataCategories];
            this.selectedSampleDataCategory = { ...newSampleDataCategory };
          })
          .catch((error) => console.error(error));
      },
      getSampleDataCategoryAction(name) {
        if (name) {
          const existingSampleDataCategory = this.sampleDataCategories.find(
            (sampleDataCategory) => sampleDataCategory.name === name
          );
          if (existingSampleDataCategory) {
            this.selectedSampleDataCategory = existingSampleDataCategory;
            return Promise.resolve();
          } else {
            return jwtInterceptor
              .get(`${VITE_APP_API_URL}/api/sample-data-categories/${name}`)
              .then((response) => {
                const sampleDataCategory = parseItem(response, 200);
                this.selectedSampleDataCategory = sampleDataCategory;
              })
              .catch((error) => console.error(error));
          }
        }
      },
      createNewSampleDataCategoryAction() {
        this.selectedSampleDataCategory = { ...newSampleDataCategory };
      },
    },
  }
);
