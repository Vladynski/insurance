<template>
  <div class="base-form">
    <div class="base-form-title def-block title-background-container insurance-list-title">
      Список оформленных страховок
    </div>
    <div class="base-form-data def-block def-block-vertical-scrolling" style="justify-content: flex-start; padding: 2%">
      <template v-if="insuranceList">
        <template v-if="Array.isArray(insuranceList)">
          <div class="block-column" style="justify-content: center; padding: 5%"
               v-if="insuranceList.length === 0">
            <InfoFrame
                defText="У вас пока нет оформленных страховок. Чтобы оформить страховку выберите пункт меню Оформление"
                startVisible="true" constant="true"/>
          </div>
          <template v-else>
            <div class="block-row block-row-right" style="height: 80px; padding: 5px">
              <RadioButtonGroup title="Сначала" :items="radioButtonGroupData" :selectIndex="0"/>
            </div>
            <div class="block-column insurance-card-container" v-if="insuranceList">
              <template v-for="(el, index) in resultList" :key="el.id">
                <InsuranceCard :insurance="el"/>
                <hr v-if="index < insuranceList.length - 1" class="separator">
              </template>
            </div>
          </template>
        </template>
        <template v-else>
          <InfoFrame
              defText="Не удалось получить данные от сервера :("
              outsideType="error"
              startVisible="true" constant="true"/>
        </template>
      </template>
    </div>
  </div>
</template>
<script>
import InsuranceCard from "./cards/InsuranceCard.vue";
import InfoFrame from "../../items/InfoFrame.vue";
import Checkbox from "../../items/Checkbox.vue";
import RadioButtonGroup from "../../items/RadioButtonGroup.vue";

export default {
  components: {RadioButtonGroup, InsuranceCard, InfoFrame, Checkbox},

  data() {
    return {
      insuranceList: undefined,
      resultList: undefined,
      radioButtonGroupData: [
        {
          name: 'активные',
          select: () => {
            this.sortListByInsuranceStatus()
          }
        },
        {
          name: 'новые',
          select: () => {
            this.sortListByDate();
          }
        }
      ]
    }
  },
  methods: {
    sortListByInsuranceStatus() {
      this.resultList = this.insuranceList.sort((a, b) => {
        if (a.status === 'VALID' && b.status === 'VALID') {
          return 0
        } else if (a.status === 'VALID') {
          return -1
        } else if (b.status === 'VALID') {
          return 1
        } else {
          return 0
        }
      })
    },
    sortListByDate() {
      this.resultList = this.insuranceList.sort((a, b) => {
        return new Date(b.end_time).getTime() - new Date(a.end_time).getTime()
      })
    }
  },
  beforeMount() {
    this.$api.getMyInsurance().then(
        (ok) => {
          this.insuranceList = ok.data
          this.sortListByInsuranceStatus()
        },
        (err) => {
          //FIXME show error
          this.insuranceList = undefined
        }
    )
  }
}
</script>

<style scoped>
@import "../../../vue_css/base_form.css";
@import "../../../vue_css/default_size.css";

.insurance-list-title:after {
  background-image: url("../../pic/list.png");
  background-color: #ab76a0;
}

.insurance-card-container {
  justify-content: flex-start;
  height: auto;
}

.insurance-card-container > * {
  margin-bottom: 25px;
}
</style>