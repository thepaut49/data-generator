<template>
  <input
    v-bind="$attrs"
    type="checkbox"
    :checked="modelValue"
    @change="$emit('update:modelValue', $event.target.checked)"
    :id="uuid"
    :disabled="disabled"
  />
  <label :for="uuid" v-if="label">{{ label }}</label>
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
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
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
label {
  font-weight: bold;
}
</style>
