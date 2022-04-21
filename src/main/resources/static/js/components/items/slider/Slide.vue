<template>
  <div v-if="view || isShowByNotIf" v-show="view" ref="content" class="base-form-data"
       :style="['width: 100%; height: 100%; transition-duration: 300ms', noPadding ? 'padding: 0px' : '']">
    <slot></slot>
  </div>
  <!--  'display:' + view ? 'block' : 'none'-->
</template>

<script>
export default {
  props: ['title', 'pageSliderGetter', 'defView', 'noPadding', 'nextAction', 'previewAction', 'init', 'showByIf'],
  data() {
    return {
      view: this.defView,
      initAction: this.init,
      simpleNext: false,
      simplePreview: false,
      nextSlide: this.nextAction,
      previewSlide: this.previewAction,
      isShowByNotIf: !this.showByIf
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
        if (this.initAction) {
          this.initAction()
          this.initAction = undefined
        }
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
  }
}
</script>

<style scoped>

</style>