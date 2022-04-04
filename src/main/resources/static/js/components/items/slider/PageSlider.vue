<template>
  <div class="block-column" style="width: 100%; height: 100%">
    <div class="base-title" style="position: relative; padding-left: 55px; padding-right: 55px; margin-top: 0">
      <Button ref="prevBtn" v-if="visiblePreviewButton" :click="previewSlide" title="Назад"
              style="left: 0; transform: scale(-1,1) translate(-50%, -50%)"
              class="btn green-btn navigate-btn">➜
      </Button>
      <div ref="title" style="transition-duration: 300ms"></div>
      <Button ref="nextBtn" v-if="visibleNextButton" :click="nextSlide" title="Вперёд"
              style="right: 0;"
              class="btn green-btn navigate-btn">➜
      </Button>
    </div>
    <hr class="separator">
    <div ref="content" class="block-column">
      <slot ref="slide"></slot>
    </div>
  </div>
</template>

<script>
import Button from "../Button.vue";

export default {
  components: {Button},
  data() {
    return {
      currentSlide: -1,
      slides: undefined,
      visibleNextButton: false,
      visiblePreviewButton: false
    }
  },
  methods: {
    hide(comp) {
      this.$refs.content.style.opacity = "0"
      this.$refs.title.style.opacity = "0"
      setTimeout(comp, 300)
    },
    show(comp) {
      comp()
      setTimeout(() => {
        this.$refs.content.style.opacity = "1"
        this.$refs.title.style.opacity = "1"
      }, 300)
    },
    setTitle(titleText) {
      this.$refs.title.textContent = titleText
    },
    initSlides(slides) {
      this.slides = slides;
      this.nextSlide()
    },
    nextSlide() {
      if (this.canNext()) {
        if (this.currentSlide > -1)
          this.slides[this.currentSlide].hide()
        this.showSlide(++this.currentSlide)
        this.updateButtons()
      }
    },
    previewSlide() {
      if (this.canPreview()) {
        this.slides[this.currentSlide].hide()
        this.showSlide(--this.currentSlide)
        this.updateButtons()
      }
    },
    showSlide(index) {
      setTimeout(() => {
        this.slides[index].show()
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

.navigate-btn {
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  font-size: 16px;
}

.separator {
  width: 100%;
  border-top: 2px solid #d0d0d0;
}
</style>