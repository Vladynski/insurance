<template>
  <div v-if="view" ref="content" class="base-form-data" style="width: 100%; height: 100%; transition-duration: 300ms">
    <slot></slot>
  </div>
</template>

<script>
export default {
  props: ['title', 'pageSliderGetter', 'defView'],
  data() {
    return {
      view: false,
      simpleNext: false,
      simplePreview: false
    }
  },
  methods: {
    hide() {
      this.pageSliderGetter().hide(() => this.view = false)
    },
    show() {
      this.pageSliderGetter().setTitle(this.title)
      this.pageSliderGetter().show(() => {
        this.view = true
      })
    },
    swap() {
      if (this.view)
        this.hide()
      else
        this.show()
    },
    getTitle() {
      return this.title;
    }
  },
  mounted() {
    this.view = this.defView
  }
}
</script>

<style scoped>

</style>