import {clearPhoneNumber} from "./Util";

export function createAdminApi(axios) {
    return {
        axios: axios,

        userFilter(firstName, secondName, patronymic, email, passportId, id, insuranceDataId, admin) {
            let data = {}
            if (firstName)
                data['first_name'] = firstName
            if (secondName)
                data['second_name'] = secondName
            if (patronymic)
                data['patronymic'] = patronymic
            if (email)
                data['email'] = email
            if (id)
                data['id'] = id
            if (insuranceDataId)
                data['insurance_data_status'] = insuranceDataId
            if (admin)
                data['admin'] = admin
            if (passportId)
                data['passport_id'] = passportId

            return this.axios.post('/users/filter', data)
        },

        editUserDataBuilder(id) {
            return {
                data: {
                    id: id
                },

                secondName(secondName) {
                    this.data['second_name'] = secondName
                    return this
                },
                firstName(firstName) {
                    this.data['first_name'] = firstName
                    return this
                },
                patronymic(patronymic) {
                    this.data['patronymic'] = patronymic
                    return this
                },
                email(email) {
                    this.data['email'] = email
                    return this
                },
                phone(phone) {
                    this.data['phone'] = clearPhoneNumber(phone)
                    return this
                },
                passportId(passportId) {
                    this.data['passport_id'] = passportId
                    return this
                },
                adminStatus(adminStatus) {
                    this.data['admin_status'] = adminStatus
                    return this
                },
                insuranceDataStatus(insuranceDataStatus) {
                    this.data['insurance_data_status'] = insuranceDataStatus
                    return this
                }
            }
        },

        editUserData(data) {
            return this.axios.put('/users', data)
        }
    }
}