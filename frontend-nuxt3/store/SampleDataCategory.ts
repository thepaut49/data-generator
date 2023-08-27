import { defineStore } from "pinia";
import {
  parseItem,
  parseDeleteResponse,
  parseList,
} from "../shared/data.service";
import SampleDataCategoryService from "../services/SampleDataCategoryService";

export const newSampleDataCategory = {
  id: undefined,
  name: "",
  version: 0,
  modifiedBy: "",
  modifiedAt: new Date().toISOString(),
};

export const useSampleDataCategory = defineStore("SampleDataCategory", {
  state() {
    return {
      sampleDataCategories: [] as SampleDataCategory[],
      selectedSampleDataCategory: {} as SampleDataCategory,
      loading: false,
      lastLoadingDate: new Date(1970, 0, 1, 0, 0, 0),
    };
  },
  getters: {},
  actions: {
    async addSampleDataCategoryAction(sampleDataCategory: SampleDataCategory) {
      const { getSession } = useSession();
      const session = await getSession();

      return SampleDataCategoryService.addSampleDataCategory(
        sampleDataCategory,
        session?.accessToken
      )
        .then((response) => {
          /*const addedSampleDataCategory: SampleDataCategory = parseItem(
            response,
            201
          );
          this.sampleDataCategories.unshift(addedSampleDataCategory); // mutable addition
          this.selectedSampleDataCategory = addedSampleDataCategory;*/
        })
        .catch((error: Error) => console.error(error));
    },
    async deleteSampleDataCategoryAction(
      sampleDataCategory: SampleDataCategory
    ) {
      const { getSession } = useSession();
      const session = await getSession();

      return SampleDataCategoryService.deleteSampleDataCategory(
        sampleDataCategory,
        session?.accessToken
      )
        .then((response) => {
          parseDeleteResponse(response, 200);
          this.sampleDataCategories = [
            ...this.sampleDataCategories.filter(
              (p: SampleDataCategory) => p.name !== sampleDataCategory.name
            ),
          ];
          this.selectedSampleDataCategory = { ...newSampleDataCategory };
        })
        .catch((error: Error) => console.error(error));
    },
    async getSampleDataCategoriesAction(categoryName: string | null) {
      this.loading = true;
      const { getSession } = useSession();
      const session = await getSession();

      return SampleDataCategoryService.getSampleDataCategories(
        categoryName,
        session?.accessToken
      )
        .then((response) => {
          this.sampleDataCategories = parseList(response);
          this.selectedSampleDataCategory = { ...newSampleDataCategory };
          this.loading = false;
          this.lastLoadingDate = new Date();
        })
        .catch((error: Error) => {
          console.log(error);
        });
    },
    async updateSampleDataCategoryAction(
      sampleDataCategory: SampleDataCategory
    ) {
      const { getSession } = useSession();
      const session = await getSession();

      return SampleDataCategoryService.updateSampleDataCategory(
        sampleDataCategory,
        session?.accessToken
      )
        .then((response) => {
          const updatedSampleDataCategory: SampleDataCategory = parseItem(
            response,
            200
          );
          const index = this.sampleDataCategories.findIndex(
            (h: SampleDataCategory) => h.id === updatedSampleDataCategory.id
          );
          this.sampleDataCategories.splice(index, 1, updatedSampleDataCategory);
          this.sampleDataCategories = [...this.sampleDataCategories];
          this.selectedSampleDataCategory = { ...newSampleDataCategory };
        })
        .catch((error: Error) => console.error(error));
    },
    async getSampleDataCategoryAction(name: string) {
      if (name) {
        const existingSampleDataCategory = this.sampleDataCategories.find(
          (sampleDataCategory) => sampleDataCategory.name === name
        );
        if (existingSampleDataCategory) {
          this.selectedSampleDataCategory = existingSampleDataCategory;
          return existingSampleDataCategory;
        } else {
          const { getSession } = useSession();
          const session = await getSession();

          return SampleDataCategoryService.getSampleDataCategory(
            name,
            session?.accessToken
          )
            .then((response) => {
              const sampleDataCategory: SampleDataCategory = parseItem(
                response,
                200
              );
              const index = this.sampleDataCategories.findIndex(
                (h: SampleDataCategory) => h.name === sampleDataCategory.name
              );
              this.sampleDataCategories.splice(index, 1, sampleDataCategory);
              this.sampleDataCategories = [...this.sampleDataCategories];
              this.selectedSampleDataCategory = { ...sampleDataCategory };
            })
            .catch((error: Error) => console.error(error));
        }
      }
    },
    createNewSampleDataCategoryAction() {
      this.selectedSampleDataCategory = { ...newSampleDataCategory };
    },
  },
});
