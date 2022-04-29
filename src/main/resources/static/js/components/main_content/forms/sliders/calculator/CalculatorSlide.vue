<template>
  <div class="block-column" style="height: auto; width: 75%;">
    <div class="block-column select-items-margin" style="height: auto">
      <InfoFrame ref="baseSumInfoFrame"/>
      <Select :ref="(el) => group.add(el, item.name)" v-for="(item, index) in selectionData" :title="item.name"
              :key="item.id"
              :items="item.variants" :getTitle="(el) => el.name" :group="group"
              :defSelected="getAlreadySelectedForIndex(index)"></Select>
    </div>
    <div style="height: 45px; margin-top: 65px" class="block-row block-row-right">
      <span class="d-text-size">{{ result }}</span>
      <Button :click="calculate" class="btn green-btn d-btn-max-size" style="width: 245px">Рассчитать</Button>
    </div>
  </div>
  <div class="block-column" style="margin-top: 45px; width: 75%; height: auto">
    <InfoFrame v-if="!hasInsuranceData" ref="infoFrame"></InfoFrame>
    <div v-else class="block-column block-column-items-margin"
         :style="['height: 45px; margin-top: 15px', hasSelected ? 'opacity: 1' : 'opacity: 0']">
      <Checkbox ref="acceptCheckBox">
        Оформляя страховку вы подтверждаете что согласны с <a @click="showBanner" class="requirement">условиями</a>
      </Checkbox>
      <Button :click="updateInsuranceData" ref="nextButton" startBlock="true"
              class="btn green-btn d-btn-max-size">
        Оформить страховку
      </Button>
    </div>
  </div>
</template>

<script>
import Select from "../../../../items/Select.vue";
import {createGroup} from "../../../../items/util/SelectGroup.js"
import Button from "../../../../items/Button.vue";
import InfoFrame from "../../../../items/InfoFrame.vue";
import Checkbox from "../../../../items/Checkbox.vue";

const defaultResultText = '👉'
let baseSum = -1;

export default {
  components: {Checkbox, Button, Select, InfoFrame},
  props: ['outUserdata', 'nextSlide', 'createInsuranceData'],
  data() {
    return {
      userdata: undefined,
      selectionData: undefined,
      group: createGroup(),
      result: defaultResultText,
      resultSum: undefined,
      hasInsuranceData: undefined,
      hasSelected: false
    }
  },

  methods: {
    getAlreadySelectedForIndex(index) {
      return this.createInsuranceData.metadata.selectedVariants[index]
    },
    updateInsuranceData() {
      this.createInsuranceData.resetLists()
      this.group.getSelected().forEach(
          el => {
            this.createInsuranceData.selectedVariantsIds.push(el.id)
            this.createInsuranceData.metadata.selectedVariants.push(el)
          }
      )
      this.createInsuranceData.resultSum = this.resultSum
      this.nextSlide()
    },
    showCreateInsuranceForm() {
      this.hasSelected = true
      this.$refs.acceptCheckBox.setButton(this.$refs.nextButton)
    },
    calculate() {
      if (this.group.allSelected(true) && baseSum > 0) {
        const base = 30
        const selected = this.group.getSelected();
        let result = base
        selected.forEach(el => {
          result += base * el.coefficient - base
        })
        this.createInsuranceData.metadata.resultSum = result
        this.result = 'Итоговая стоимость: ' + result + ' BYN'
        this.showCreateInsuranceForm()
      } else {
        this.result = 'Выберите вариант каждого пункта 👉'
      }
    },
    getPageSlider() {
      return this.$refs.pageSlider
    },
    showBanner() {
      this.$api.getInsuranceContract().then((ok) => {
        this.$banner.showHtmlText("Что-то типа договора", ok.data)
      })
    }
  },
  mounted() {
    this.userdata = this.outUserdata
    const selectionData = this.$api.getSelectionData()

    if (selectionData) {
      this.group.setLimit(selectionData.selections.length)
      this.selectionData = selectionData.selections
      baseSum = selectionData.base_sum
    } else {
      this.$refs.baseSumInfoFrame.setDefaultText('Не удалось получить данные о стоимости услуг. Пожалуйста, обратитесь в поддержку в разделе "Задать вопрос"')
      this.$refs.baseSumInfoFrame.setConstant(true, 'error')
    }

    if (this.userdata.insurance.status === 'NONE') {
      this.hasInsuranceData = false
      this.$refs.infoFrame.setDefaultText('Для оформления страховки необходимо перейти на вкладку Профиль и заполнить данные в форме Страховая информация')
      this.$refs.infoFrame.setConstant(true)
    } else if (this.userdata.insurance.status === 'WAIT_CONFIRMATION') {
      this.hasInsuranceData = false
      this.$refs.infoFrame.setDefaultText('Для оформления страховки необходимо дождаться подтверждения страховой информации от администрации')
      this.$refs.infoFrame.setConstant(true)
    } else {
      this.hasInsuranceData = true
    }

    this.group.addListenerForAll(() => {
      this.result = defaultResultText
      this.hasSelected = false
    })
  }
}
</script>

<style scoped>
@import "../../../../../vue_css/base_form.css";
@import "../../../../../vue_css/default_size.css";

.select-items-margin > div {
  margin-top: 35px;
}

.requirement {
  text-decoration: underline;
  background-color: white;
  display: inline-block;
  border-radius: 2px;
  padding: 2px;
}
</style>