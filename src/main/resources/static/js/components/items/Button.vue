<template>
  <button :title="title" @click="clickAction" class="btn"
          :disabled="blocked"
          :class="[this.class, blocked ? ' btn-disabled '  : '', checked ? checkedClass : '']">
    <slot>Button name is not set</slot>
  </button>
</template>

<script>
export default {
  props: ['click', 'class', 'checkedClass', 'startBlock', 'title'],
  data() {
    return {
      blocked: false,
      checked: false,
    }
  },
  methods: {
    block() {
      this.blocked = true
    },
    unblock() {
      this.blocked = false
    },
    check() {
      this.checked = !this.checked
    },
    clickAction() {
      if (!this.blocked) {
        if (this.checkedClass)
          this.check()
        this.click(this.checked)
      }
    }
  },
  mounted() {
    if (this.startBlock)
      this.block()
  }
}
</script>