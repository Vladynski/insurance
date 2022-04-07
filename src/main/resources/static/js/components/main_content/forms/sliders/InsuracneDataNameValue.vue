<template>
  <div class="insurance-data block-row">
    <div class="insurance-data-key">
      {{ name }}:
    </div>
    <div class="insurance-data-value">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: ['name']
}
</script>

<style scoped>
.insurance-data {
  height: 34px;
  border-bottom: 2px solid #512B51;
  width: 100%;
}

.insurance-data-key {
  width: 50%;
}

.insurance-data-value {
  width: 50%;
}
</style>