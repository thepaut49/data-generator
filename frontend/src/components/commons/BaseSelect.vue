<template>
  <label :for="uuid" v-if="label">{{ label }}</label>
  <select v-bind="$attrs" v-model="localState" :id="uuid">
    <option v-for="option in options" :key="option">
      {{ option }}
    </option>
  </select>
</template>

<script>
import UniqueID from "../../features/UniqueID";

export default {
  props: {
    label: {
      type: String,
      default: "",
    },
    options: {
      type: Array,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
    value: {
      type: [String, Number],
      required: true,
    },
  },
  data() {
    const uuid = UniqueID().getID();
    return {
      uuid,
    };
  },
  computed: {
    localState: {
      get() {
        return this.value;
      },
      set(localState) {
        this.$emit("input", localState);
      },
    },
  },
};
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
