import axios, { AxiosResponse } from "axios";
const VITE_APP_API_URL: string = "http://localhost:9090";

const api = axios.create({
  baseURL: VITE_APP_API_URL,
});

export default {
  getSampleDataCategories(
    categoryName: string | null,
    accessToken: string
  ): Promise<AxiosResponse<SampleDataCategory[]>> {
    api.interceptors.request.use((config) => {
      config.headers["Authorization"] = `bearer ${accessToken}`;
      return config;
    });

    let url = VITE_APP_API_URL + "/api/sample-data-categories";
    if (categoryName && categoryName !== "") {
      url = url + "?categoryName=" + categoryName;
    }
    return api.get<SampleDataCategory[]>(url);
  },
  getSampleDataCategory(
    categoryName: string,
    accessToken: string
  ): Promise<AxiosResponse<SampleDataCategory>> {
    api.interceptors.request.use((config) => {
      config.headers["Authorization"] = `bearer ${accessToken}`;
      return config;
    });

    let url = VITE_APP_API_URL + "/api/sample-data-categories/" + categoryName;
    return api.get<SampleDataCategory>(url);
  },
  addSampleDataCategory(
    sampleDataCategory: SampleDataCategory,
    accessToken: string
  ): Promise<AxiosResponse<SampleDataCategory>> {
    api.interceptors.request.use((config) => {
      config.headers["Authorization"] = `bearer ${accessToken}`;
      return config;
    });

    return api.post(
      `${VITE_APP_API_URL}/api/sample-data-categories`,
      sampleDataCategory
    );
  },
  deleteSampleDataCategory(
    sampleDataCategory: SampleDataCategory,
    accessToken: string
  ): Promise<AxiosResponse<any, any>> {
    api.interceptors.request.use((config) => {
      config.headers["Authorization"] = `bearer ${accessToken}`;
      return config;
    });

    return api.delete(
      `${VITE_APP_API_URL}/api/sample-data-categories/${sampleDataCategory.name}`
    );
  },
  updateSampleDataCategory(
    sampleDataCategory: SampleDataCategory,
    accessToken: string
  ): Promise<AxiosResponse<SampleDataCategory>> {
    api.interceptors.request.use((config) => {
      config.headers["Authorization"] = `bearer ${accessToken}`;
      return config;
    });
    return api.put<SampleDataCategory>(
      `${VITE_APP_API_URL}/api/sample-data-categories/${sampleDataCategory.name}`,
      sampleDataCategory
    );
  },
};
