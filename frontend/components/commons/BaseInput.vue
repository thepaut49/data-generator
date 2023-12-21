<template>
  <div :class="cssClass">
    <label :for="uuid" v-if="label">{{ label }}</label>
    <input
      :value="modelValue"
      :placeholder="label"
      @input="$emit('update:modelValue', $event.target.value)"
      :id="uuid"
      :aria-describedby="error ? `${uuid}-error` : null"
      :aria-invalid="error ? true : null"
      :disabled="disabled"
    />
    <p
      v-if="error && error.length > 0"
      class="errorMessage"
      :id="`${uuid}-error`"
      aria-live="assertive"
    >
      {{ error }}
    </p>
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
    error: {
      type: String,
      default: "",
    },
    disabled: {
      type: Boolean,
      default: false,
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
label {
  font-weight: bold;
}

input {
  /* haut | droit | bas | gauche */
  margin: 0.5em 0 0 0;
  flex: 1;
}

p {
  /* haut | droit | bas | gauche */
  margin: 0.5em 0 0 0.5em;
  color: red;
  font-weight: bold;
  font-style: italic;
}
</style>
