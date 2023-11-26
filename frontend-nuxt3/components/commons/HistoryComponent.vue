<template>
  <section class="history-section">
    <section class="history-left-subsection">
      <BaseSelect
        label="Left version : "
        :options="options"
        v-model="leftVersion"
      />
      <JsonComparison
        :selected-version="leftVersion"
        :version-to-compare-to="rightVersion"
        highlightColor="green"
      />
    </section>
    <section class="history-right-subsection">
      <BaseSelect
        label="Right version : "
        :options="options"
        v-model="rightVersion"
      />
      <JsonComparison
        :selected-version="rightVersion"
        :version-to-compare-to="leftVersion"
        highlightColor="red"
      />
    </section>
  </section>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import BaseSelect from "~~/components/commons/BaseSelect.vue";
import JsonComparison from "~~/components/commons/JsonComparison.vue";
import { SampleDataCategory } from "~~/types/entity/SampleDataCategory";
import { SampleDataCategoryAudit } from "~~/types/entity/SampleDataCategoryAudit";

const props = defineProps({
  entity: {
    type: Object as () => SampleDataCategory,
    required: true,
  },
});

let options = ref<{ label: number; value: SampleDataCategoryAudit }[]>([]);

const initGeneric = new SampleDataCategoryAudit();

const leftVersion = ref(initGeneric);
const rightVersion = ref(initGeneric);

watchEffect(() => {
  console.log("Entity:", props.entity);
  console.log("Versions:", props.entity.versions);
  console.log("Version:", props.entity.versions);

  if (
    props.entity.version &&
    props.entity.versions &&
    props.entity.versions.length > 0
  ) {
    options.value = props.entity.versions.map(
      (element: SampleDataCategoryAudit) => ({
        label: element.version,
        value: element,
      })
    );

    leftVersion.value =
      props.entity.versions.find(
        (element: SampleDataCategoryAudit) =>
          element.version === props.entity.version
      ) || initGeneric;

    rightVersion.value =
      props.entity.versions.find(
        (element: SampleDataCategoryAudit) =>
          element.version === props.entity.version
      ) || initGeneric;
  }
});
</script>

<style scoped>
.history-section {
  display: flex;
  flex-direction: row;
  margin: var(--min-margin);
  flex-grow: 1;
}

.history-left-subsection {
  display: flex;
  flex-direction: column;
  margin: var(--min-margin);
  flex-grow: 1;
}

.history-right-subsection {
  display: flex;
  flex-direction: column;
  margin: var(--min-margin);
  flex-grow: 1;
}
</style>
