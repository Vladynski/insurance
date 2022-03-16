<template>
  <button @click="click" class="btn" :disabled="blocked" :class="this.class + (blocked ? ' btn-disabled '  : '')"><slot></slot></button>
</template>

<script>
export default {
  props: ['click', 'class'],
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
    }
  }
}
</script>