<template>
  <div class="block-column" style="width: 100%; height: 100%">
    <div class="base-title" v-if="!hideTitle"
         style="position: relative; padding-left: 55px; padding-right: 55px; margin-top: 0">
      <Button ref="prevBtn" v-if="visiblePreviewButton" :click="previewSlide" title="Назад"
              style="left: 0; transform: scale(-1,1) translate(-50%, -50%)"
              class="btn green-btn navigate-btn">➜
      </Button>
      <div ref="title" :style="['transition-duration: 300ms', 'opacity: ' + opacity]"></div>
      <Button ref="nextBtn" v-if="visibleNextButton" :click="nextSlide" title="Вперёд"
              style="right: 0;"
              class="btn green-btn navigate-btn">➜
      </Button>
    </div>
    <div v-else class="without-title">
      <Button ref="prevBtn" v-if="visiblePreviewButton" :click="previewSlide" title="Назад"
              style="left: 0; transform: scale(-1,1) translate(0, 0)"
              class="btn green-btn navigate-btn navigate-btn-without-title">➜
      </Button>
      <Button ref="nextBtn" v-if="visibleNextButton" :click="nextSlide" title="Вперёд"
              style="right: 0;"
              class="btn green-btn navigate-btn navigate-btn-without-title">➜
      </Button>
    </div>
    <hr v-if="!hideTitle" class="separator">
    <div ref="content" class="block-column" :style="'opacity: ' + opacity">
      <slot ref="slide"></slot>
    </div>
  </div>
</template>

<script>
import Button from "../Button.vue";

export default {
  components: {Button},
  props: ['hideTitle'],
  data() {
    return {
      currentSlide: -1,
      slides: undefined,
      visibleNextButton: false,
      visiblePreviewButton: false,
      opacity: 1,
      titleOpacity: 1
    }
  },
  methods: {
    hide(comp) {
      this.opacity = 0
      if (!this.hideTitle)
        this.titleOpacity = 0
      setTimeout(comp, 300)
    },
    show(comp) {
      comp()
      setTimeout(() => {
        this.opacity = 1
        if (!this.hideTitle)
          this.titleOpacity = 1
      }, 300)
    },
    setTitle(titleText) {
      if (!this.hideTitle)
        this.$refs.title.textContent = titleText
    },
    initSlides(slides) {
      this.slides = slides;
      this.nextSlide()
    },
    nextSlide() {
      if (this.currentSlide >= 0 && this.slides[this.currentSlide].nextAction)
        this.slides[this.currentSlide].nextAction()
      else if (this.canNext())
        this.showSlide(this.currentSlide + 1)
    },
    previewSlide() {
      if (this.currentSlide >= 0 && this.slides[this.currentSlide].previewAction)
        this.slides[this.currentSlide].previewAction()
      if (this.canPreview())
        this.showSlide(this.currentSlide - 1)
    },
    showSlide(index) {
      if (this.currentSlide > -1)
        this.slides[this.currentSlide].hide()

      setTimeout(() => {
        this.slides[index].show()
        this.currentSlide = index
        this.updateButtons()
      }, 300)
    },
    canNext() {
      return this.currentSlide + 1 < this.slides.length
    },
    canPreview() {
      return this.currentSlide > 0
    },
    updateButtons() {
      this.visibleNextButton = this.canNext() && this.slides[this.currentSlide + 1].simpleNext
      this.visiblePreviewButton = this.canPreview() && this.slides[this.currentSlide].simplePreview
    }
  }
}
</script>

<style scoped>
@import "../../../vue_css/base_form.css";

.without-title {
  height: 0;
  width: 100%;
  overflow: visible;
  position: relative;
}

.navigate-btn {
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  font-size: 16px;
}

.navigate-btn-without-title {
  transform: translate(0, 0);
  z-index: 2;
  opacity: 0.5;
}

.navigate-btn-without-title:hover {
  transform: translate(0, 0);
  z-index: 2;
  opacity: 1;
}
</style>