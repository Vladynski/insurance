<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container calculator-title-background"
         style="font-size: 3.5vmin">
      Оформление обязательного страхования гражданской ответственности
    </div>
    <div class="base-form-data def-block" style="padding: 5%">
      <PageSlider ref="pageSlider">
        <Slide ref="calculatorSlide"
               title="Калькулятор стоимости обязательного страхования гражданской ответственности"
               :pageSliderGetter="getPageSlider"
               :defView="false">
          <CalculatorSlide ref="calculatorSlide"
                           :outUserdata="outUserdata"
                           :nextSlide="()=> this.getPageSlider().nextSlide()"
                           :createInsuranceData="createInsuranceData"></CalculatorSlide>
        </Slide>
        <Slide ref="createInsuranceSlide"
               title="Оформление страховки"
               :pageSliderGetter="getPageSlider">
          <CreateInsuranceSlide
              :outUserdata="outUserdata"
              :createInsuranceData="createInsuranceData"
              :nextSlide="()=> this.getPageSlider().nextSlide()"></CreateInsuranceSlide>
        </Slide>
        <Slide ref="checkInsuranceDataSlide"
               title="Проверка данных"
               :pageSliderGetter="getPageSlider">
          <CheckInsuranceDataSlide
              :createInsuranceData="createInsuranceData"
              :nextSlide="()=> this.getPageSlider().nextSlide()"></CheckInsuranceDataSlide>
        </Slide>
        <Slide ref="createInsuranceFinishSlide"
               title="Финаль этап оформления"
               :pageSliderGetter="getPageSlider"
               :showByIf="true">
          <CreateInsuranceFinishSlide
              :outUserdata="outUserdata"
              :createInsuranceData="createInsuranceData"></CreateInsuranceFinishSlide>
        </Slide>
      </PageSlider>
    </div>
  </div>
</template>

<script>
import PageSlider from "../../items/slider/PageSlider.vue";
import Slide from "../../items/slider/Slide.vue";
import CalculatorSlide from "./sliders/calculator/CalculatorSlide.vue";
import CreateInsuranceSlide from "./sliders/calculator/CreateInsuranceSlide.vue";
import {createInsuranceDataObject} from "../../../api/ApiWorker";
import CreateInsuranceFinishSlide from "./sliders/calculator/CreateInsuranceFinishSlide.vue";
import CheckInsuranceDataSlide from "./sliders/calculator/CheckInsuranceDataSlide.vue";

export default {
  components: {
    CheckInsuranceDataSlide,
    CreateInsuranceFinishSlide, CreateInsuranceSlide, CalculatorSlide, PageSlider, Slide
  },
  props: ['outUserdata'],
  data() {
    return {
      createInsuranceData: createInsuranceDataObject()
    }
  },
  methods: {
    getPageSlider() {
      return this.$refs.pageSlider
    }
  },
  mounted() {
    this.$refs.createInsuranceSlide.simplePreview = true
    this.$refs.checkInsuranceDataSlide.simplePreview = true
    this.$refs.createInsuranceFinishSlide.simplePreview = true
    this.$refs.pageSlider.initSlides(
        [
          this.$refs.calculatorSlide,
          this.$refs.createInsuranceSlide,
          this.$refs.checkInsuranceDataSlide,
          this.$refs.createInsuranceFinishSlide
        ]
    )
  }
}
</script>

<style>
@import "../../../vue_css/base_form.css";
@import "../../../vue_css/default_size.css";

.calculator-title-background:after {
  background-image: url("../../pic/secure-insurance.png");
  background-color: lightblue;
}
</style>