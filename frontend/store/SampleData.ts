import { defineStore } from "pinia";
import type { Ref } from "vue";
import { handleError } from "../utils/StoreUtils";
import { updateElement } from "../utils/CollectionUtils";

export const newSampleData: SampleData = {
  id: undefined,
  key: "",
  value: "",
  blobValue: "",
  isBlobTypeValue: false,
  version: 0,
  modifiedBy: "",
  modifiedAt: new Date().toISOString(),
  versions: [],
  categoryId: undefined,
  categoryName: undefined,
};

export const useSampleData = defineStore("SampleData", () => {
  const { getSession, signIn } = useAuth();
  const config = useRuntimeConfig();
  const BACKEND_API_URL = config.public.BACKEND_API_URL;
  const sampleDatas: Ref<SampleData[]> = ref([]);
  const filteredSampleDatas: Ref<SampleData[]> = ref([]);
  const selectedSampleData: Ref<SampleData | null> = ref<SampleData | null>(
    null
  );
  const loading = ref(false);
  const lastLoadingDate = ref(new Date(1970, 0, 1, 0, 0, 0));
  const keyCriteria = ref("");
  const isBlobTypeValueCriteria = ref(false);
  const valueCriteria = ref("");
  const categoryCriteria = ref(-1);

  async function addSampleDataAction(sampleData: SampleData, retry: boolean) {
    const session = await getSession();
    loading.value = true;
    try {
      const { data, error } = await useFetch<SampleData>(
        `${BACKEND_API_URL}/api/sample-datas`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.accessToken}`,
          },
          body: sampleData, // le type utilisé pour le corps doit correspondre à l'en-tête "Content-Type"
        }
      );
      if (data.value) {
        sampleDatas.value.unshift(data.value); // mutable addition
        filteredSampleDatas.value.unshift(data.value); // mutable addition
        selectedSampleData.value = data.value;
      } else if (error.value) {
        handleError(
          addSampleDataAction,
          signIn,
          error.value,
          retry,
          sampleData
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function deleteSampleDataAction(
    sampleData: SampleData,
    retry: boolean
  ) {
    const session = await getSession();
    loading.value = true;
    try {
      const { data, error } = await useFetch<SampleData>(
        `${BACKEND_API_URL}/api/sample-datas/${sampleData.id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${session?.accessToken}`,
          },
        }
      );
      if (data.value) {
        sampleDatas.value = [
          ...sampleDatas.value.filter(
            (p: SampleData) => p.id !== sampleData.id
          ),
        ];
        filteredSampleDatas.value = [
          ...filteredSampleDatas.value.filter(
            (p: SampleData) => p.id !== sampleData.id
          ),
        ];
        selectedSampleData.value = { ...newSampleData };
      } else if (error.value) {
        handleError(
          deleteSampleDataAction,
          signIn,
          error.value,
          retry,
          sampleData
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }
  async function getSampleDatasAction(
    categoryId: number | null,
    key: string | null,
    isBlobTypeValue: boolean | null,
    value: string | null,
    retry: boolean
  ) {
    const session = await getSession();

    let url: string = `${BACKEND_API_URL}/api/sample-datas`;
    let firstArg = true;
    if (categoryId && categoryId !== -1) {
      url = url + "?categoryId=" + categoryId;
      firstArg = false;
    }
    if (key && key !== "") {
      if (firstArg) {
        url = url + "?key=" + key;
      } else {
        url = url + "&key=" + key;
      }
    }
    if (value && value !== "") {
      if (firstArg) {
        url = url + "?value=" + value;
      } else {
        url = url + "&value=" + value;
      }
    }
    if (isBlobTypeValue !== undefined && isBlobTypeValue !== null) {
      if (firstArg) {
        url = url + "?isBlobTypeValue=" + isBlobTypeValue;
      } else {
        url = url + "&isBlobTypeValue=" + isBlobTypeValue;
      }
    }

    try {
      const { data, error } = await useFetch<SampleData[]>(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${session?.accessToken}`,
        },
      });

      if (data.value) {
        filteredSampleDatas.value = data.value;
        selectedSampleData.value = { ...newSampleData };
      } else if (error.value) {
        handleError(
          getSampleDatasAction,
          signIn,
          error.value,
          retry,
          categoryId,
          key,
          isBlobTypeValue,
          value
        );
      }
    } catch (error) {
      throw error;
    }
  }

  async function getAllSampleDatasAction(retry: boolean) {
    loading.value = true;
    const session = await getSession();

    let url: string = `${BACKEND_API_URL}/api/sample-datas`;

    try {
      const { data, error } = await useFetch<SampleData[]>(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${session?.accessToken}`,
        },
      });

      if (data.value) {
        sampleDatas.value = data.value;
        filteredSampleDatas.value = data.value;
        selectedSampleData.value = { ...newSampleData };
        lastLoadingDate.value = new Date();
      } else if (error.value) {
        handleError(getAllSampleDatasAction, signIn, error.value, retry);
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function updateSampleDataAction(
    sampleData: SampleData,
    retry: boolean
  ) {
    const session = await getSession();
    loading.value = true;
    try {
      const { data, error } = await useFetch<SampleData>(
        `${BACKEND_API_URL}/api/sample-datas/${sampleData.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.accessToken}`,
          },
          body: sampleData,
        }
      );

      if (data.value) {
        updateElement(data.value, sampleDatas.value);
        updateElement(data.value, filteredSampleDatas.value);
        selectedSampleData.value = { ...newSampleData };
      } else if (error.value) {
        handleError(
          updateSampleDataAction,
          signIn,
          error.value,
          retry,
          sampleData
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function getSampleDataAction(id: number | undefined, retry: boolean) {
    if (id) {
      const existingSampleData = sampleDatas.value.find(
        (sampleData) => sampleData.id === id
      );
      if (existingSampleData) {
        selectedSampleData.value = existingSampleData;
      } else {
        const session = await getSession();
        loading.value = true;
        try {
          const { data, error } = await useFetch<SampleData>(
            `${BACKEND_API_URL}/api/sample-datas/${id}`,
            {
              method: "GET",
              headers: {
                Authorization: `Bearer ${session?.accessToken}`,
              },
            }
          );
          if (data.value) {
            updateElement(data.value, sampleDatas.value);
            updateElement(data.value, filteredSampleDatas.value);
            selectedSampleData.value = { ...data.value };
          } else if (error.value) {
            handleError(getSampleDataAction, signIn, error.value, retry, id);
          }
        } catch (error) {
          throw error;
        } finally {
          loading.value = false;
        }
      }
    }
  }

  function createNewSampleDataAction() {
    selectedSampleData.value = { ...newSampleData };
  }

  return {
    sampleDatas,
    filteredSampleDatas,
    selectedSampleData,
    loading,
    lastLoadingDate,
    keyCriteria,
    isBlobTypeValueCriteria,
    valueCriteria,
    categoryCriteria,
    addSampleDataAction,
    deleteSampleDataAction,
    getSampleDatasAction,
    getAllSampleDatasAction,
    updateSampleDataAction,
    getSampleDataAction,
    createNewSampleDataAction,
  };
});
