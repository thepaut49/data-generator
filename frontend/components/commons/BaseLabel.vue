<template>
  <div :class="cssClass">
    <label class="first-label" :for="uuid" v-if="label">{{ label }}</label>
    <label :value="modelValue" class="second-label" :id="uuid">
      {{ modelValue }}
    </label>
  </div>
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
    cssClass: {
      type: String,
      required: true,
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
