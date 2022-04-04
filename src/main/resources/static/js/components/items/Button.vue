<template>
  <button @click="clickAction" class="btn" :disabled="blocked" :class="this.class + (blocked ? ' btn-disabled '  : '')">
    <slot>Button name is not set</slot>
  </button>
</template>

<script>
export default {
  props: ['click', 'class', 'startBlock'],
  data() {
    return {
      blocked: false
    }
  },
  methods: {
    block() {
      this.blocked = true
    },
    unblock() {
      this.blocked = false
    },
    clickAction() {
      if (!this.blocked)
        this.click()
    }
  },
  mounted() {
    if (this.startBlock)
      this.block()
  }
}
</script>