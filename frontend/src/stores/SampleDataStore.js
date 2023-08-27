import { defineStore } from "pinia";
import jwtInterceptor from "../shared/jwtInterceptor";
import { VITE_APP_API_URL } from "./helper";
import { parseItem, parseDeleteResponse } from "../shared/data.service";
import { useRouter } from "vue-router";

const newSampleData = {
  id: undefined,
  categoryName: "",
  key: "",
  value: "",
  isBlobValue: false,
  blobValue: "",
  version: 0,
  createdAt: new Date().toISOString(),
  modifiedAt: new Date().toISOString(),
};

const router = useRouter();

export const useSampleDataStore = defineStore("SampleDataStore", {
  state() {
    return {
      sampleDatas: [],
      selectedSampleData: {},
    };
  },
  getters: {},
  actions: {
    addSampleDataAction(sampleData) {
      return jwtInterceptor
        .post(
          `${VITE_APP_API_URL}/api/sample-data-catgeories/${sampleData.categoryName}/sample-datas`,
          sampleData
        )
        .then((response) => {
          const addedSampleData = parseItem(response, 201);
          this.sampleDatas.unshift(addedSampleData); // mutable addition
          this.selectedSampleData = addedSampleData;
        })
        .catch((error) => console.error(error));
    },
    deleteSampleDataAction(sampleData) {
      return jwtInterceptor
        .delete(
          `${VITE_APP_API_URL}/api/sample-data-catgeories/${sampleData.categoryName}/sample-datas/${sampleData.key}`
        )
        .then((response) => {
          parseDeleteResponse(response, 200);
          this.sampleDatas = [
            ...this.sampleDatas.filter((p) => p.key !== sampleData.key),
          ];
          this.selectedSampleData = { ...newSampleData };
        })
        .catch((error) => console.error(error));
    },
    getSampleDatasAction(categoryName, key, value, isBlobValue) {
      let url = `${VITE_APP_API_URL}/api/sample-data-categories/search-sample-datas?categoryName=${categoryName}&key=${key}&value=${value}&isBlobValue=${isBlobValue}`;
      return jwtInterceptor
        .get(url)
        .then((response) => {
          this.sampleDatas = response.data.items;
          this.selectedSampleData = { ...newSampleData };
        })
        .catch((error) => {
          console.log(error);
        });
    },
    updateSampleDataAction(sampleData) {
      return jwtInterceptor
        .put(
          `${VITE_APP_API_URL}/api/sample-data-catgeories/${sampleData.categoryName}/sample-datas/${sampleData.key}`,
          sampleData
        )
        .then((response) => {
          const updatedSampleData = parseItem(response, 200);
          const index = this.sampleDatas.findIndex(
            (h) => h.key === updatedSampleData.key
          );
          this.sampleDatas.splice(index, 1, updatedSampleData);
          this.sampleDatas = [...this.sampleDatas];
          this.selectedSampleData = { ...newSampleData };
        })
        .catch((error) => console.error(error));
    },
    getSampleDataAction(categoryName, key) {
      if (key) {
        const existingSampleData = this.sampleDatas.find(
          (sampleData) => sampleData.key === key
        );
        if (existingSampleData) {
          this.selectedSampleData = existingSampleData;
          return Promise.resolve();
        } else {
          return jwtInterceptor
            .get(
              `${VITE_APP_API_URL}/api/sample-data-catgeories/${categoryName}/sample-datas/${key}`
            )
            .then((response) => {
              const sampleData = parseItem(response, 200);
              this.selectedSampleData = sampleData;
            })
            .catch((error) => {
              if (error.response && error.response.status == 404) {
                router.push({
                  name: "404Resource",
                  params: { resource: "event" },
                });
              } else {
                router.push({ name: "NetworkError" });
              }
            });
        }
      }
    },
    createNewSampleDataAction(categoryName) {
      this.selectedSampleData = { ...newSampleData, categoryName };
    },
  },
});
