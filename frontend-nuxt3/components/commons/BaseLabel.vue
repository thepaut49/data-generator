<template>
  <label class="first-label" :for="uuid" v-if="label">{{ label }}</label>
  <label v-bind="$attrs" :value="modelValue" class="second-label" :id="uuid">
    {{ modelValue }}
  </label>
</template>

<script>
import UniqueID from "../../features/UniqueID";
export default {
  props: {
    label: {
      type: String,
      default: "",
    },
    modelValue: {
      type: [String, Number],
      default: "",
    },
  },
  setup() {
    const uuid = UniqueID().getID();
    return {
      uuid,
    };
  },
};
</script>

<style scoped>
.first-label {
  font-weight: bold;
}
.second-label {
  margin-left: 0.2em;
}
</style>
