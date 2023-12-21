<template>
  <section class="history-section">
    <!--section class="history-left-subsection">
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
    </!--section>
    <section-- class="history-right-subsection">
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
    </section-->
  </section>
</template>

<script setup lang="ts">
/*import { ref, watchEffect } from "vue";
import BaseSelect from "~~/components/commons/BaseSelect.vue";
import JsonComparison from "~~/components/commons/JsonComparison.vue";

const props = defineProps({
  entity: {
    type: Object as () => Generic,
    required: true,
  },
});

let options = reactive<{ label: number; value: GenericAudit }[]>([]);

const initGeneric = {
  name: "",
  id: 0,
  version: 0,
  modifiedBy: "",
  modifiedAt: "",
};

let leftVersion = reactive(initGeneric);
let rightVersion = reactive(initGeneric);

watchEffect(() => {
  console.log("Entity:", props.entity);
  console.log("Versions:", props.entity.versions);
  console.log("Version:", props.entity.version);

  try {
    if (
      props.entity.version &&
      props.entity.versions &&
      props.entity.versions.length > 0
    ) {
      options = props.entity.versions.map((element: GenericAudit) => ({
        label: element.version,
        value: element,
      }));

      leftVersion =
        props.entity.versions.find(
          (element: GenericAudit) => element.version === props.entity.version
        ) || initGeneric;

      rightVersion =
        props.entity.versions.find(
          (element: GenericAudit) => element.version === props.entity.version
        ) || initGeneric;
    }
  } catch (error) {
    console.log("error catch : {}", error);
  }
});*/
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
