<template>
  <label :for="uuid" v-if="label">{{ label }}</label>
  <textarea
    v-bind="$attrs"
    :value="modelValue"
    :placeholder="label"
    @input="$emit('update:modelValue', $event.target.value)"
    :id="uuid"
    :aria-describedby="error ? `${uuid}-error` : null"
    :aria-invalid="error ? true : null"
    :disabled="disabled"
    :rows="numberOfRows"
    :cols="numberOfColumns"
  />
  <p
    v-if="error && error.length > 0"
    class="errorMessage"
    :id="`${uuid}-error`"
    aria-live="assertive"
  >
    {{ error }}
  </p>
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
    numberOfRows: {
      type: Number,
      default: 10,
    },
    numberOfColumns: {
      type: Number,
      default: 150,
    },
    error: {
      type: String,
      default: "",
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

textarea {
  /* haut | droit | bas | gauche */
  margin: 0.5em 0 0 0;
}

p {
  /* haut | droit | bas | gauche */
  margin: 0.5em 0 0 0.5em;
  color: red;
  font-weight: bold;
  font-style: italic;
}
</style>
