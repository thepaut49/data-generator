<template>
  <div :class="cssClass">
    <label :for="uuid" v-if="label">{{ label }}</label>
    <select
      v-bind="{
        onChange: ($event) => {
          $emit('update:modelValue', $event.target.value);
        },
      }"
      :value="modelValue"
      :id="uuid"
      :disabled="disabled"
      :multiple="multiple"
    >
      <option
        v-for="option in options"
        :key="option.label"
        :value="option.value"
        :selected="option === modelValue"
      >
        {{ option.label }}
      </option>
    </select>
  </div>
</template>

<script setup>
import { defineProps, watch } from "vue";
import UniqueID from "../../features/UniqueID";

const props = defineProps({
  label: string,
  multiple: {
    type: Boolean,
    default: false,
  },
  options: {
    type: Array,
    required: true,
  },
  modelValue: [Object | null],
  disabled: {
    type: Boolean,
    default: false,
  },
  display: {
    type: string,
    default: "vertical",
  },
  cssClass: {
    type: string,
    required: true,
  },
});

const uuid = UniqueID().getID();

watch(
  () => props.modelValue,
  (newValue) => {
    // Emit an 'input' event with the selected value
    if (newValue !== props.modelValue) {
      emit("update:modelValue", newValue);
    }
  }
);
</script>

<style scoped>
label {
  font-weight: bold;
}

select {
  /* haut | droit | bas | gauche */
  margin: 0.5em 0 0 0;

  display: block;
  box-sizing: border-box;
  width: 100%;
  height: 2.6em;
  padding: 0.5em;
  font: 1em "Avenir", Helvetica, sans-serif;
}
</style>
