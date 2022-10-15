<template>
  <label :for="uuid" v-if="label">{{ label }}</label>
  <div class="divInputMontant">
    <label v-bind="$attrs" :value="modelValue" class="field" :id="uuid">
      {{ modelValue }}
    </label>
    <p>€</p>
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
.divInputMontant {
  display: flex;
  flex-direction: row;
  align-items: baseline;
}

div label {
  /* haut | droit | bas | gauche */
  margin: 0.5em 0 0 0;
}
</style>
