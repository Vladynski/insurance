import {clearPhoneNumber} from "./Util";

export function createAdminApi(axios) {
    return {
        axios: axios,

        userFilter(id, firstName, secondName, patronymic, email, passportId, insuranceDataId, admin, insuranceDataIsNotConfirmed) {
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
            if (insuranceDataIsNotConfirmed)
                data['insurance_details_not_confirmed'] = insuranceDataIsNotConfirmed

            return this.axios.post('/users/filter', data)
        },

        insuranceFilter(id, insuranceDataId, userId, ownerFirstName,
                        ownerSecondName, ownerPatronymic, winNumber, registrationNumber,
                        passportId, phone, status) {
            let data = {}

            if (id)
                data['id'] = id
            if (insuranceDataId)
                data['creator'] = insuranceDataId
            if (userId)
                data['user_id'] = userId
            if (ownerFirstName)
                data['owner_first_name'] = ownerFirstName
            if (ownerSecondName)
                data['owner_second_name'] = ownerSecondName
            if (ownerPatronymic)
                data['owner_patronymic'] = ownerPatronymic
            if (winNumber)
                data['win_number'] = winNumber
            if (registrationNumber)
                data['registration_number'] = registrationNumber
            if (passportId)
                data['passport_id'] = passportId
            if (phone)
                data['phone'] = phone
            if (status)
                data['status'] = status

            return this.axios.post('/insurance/filter', data)
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
        },

        addFaq(question, answer) {
            return this.axios.post('/faq', {
                question: question,
                answer: answer
            })
        },

        updateFaq(id, question, answer) {
            return this.axios.put('/faq', {
                id: id,
                question: question,
                answer: answer
            })
        },

        deleteFaq(id) {
            return this.axios.delete('/faq?id=' + id)
        },

        getQuestions() {
            return this.axios.get('/questions/admin')
        },

        setQuestionAnswer(id, text) {
            return this.axios.put(`/questions?id=${id}&answer=${text}`)
        },

        getSelectionData() {
            return this.axios.get("/selection")
        },

        updateSelectionVariant(id, groupId, name, coefficient) {
            const data = {}

            if (id)
                data.id = id
            if (groupId)
                data.group_id = groupId
            if (name)
                data.name = name
            if (coefficient)
                data.coefficient = coefficient.toString().replace(',', '.')

            return this.axios.put('/selection/variant', data)
        },

        deleteSelectionVariant(id) {
            return axios.delete('/selection/variant?id=' + id)
        },

        deleteGroup(id) {
            return axios.delete('/selection/group?id=' + id)
        },

        getGroup(id) {
            return axios.get('/selection/group?id=' + id)
        },

        updateGroup(id, name) {
            return this.axios.put(`/selection/group?id=${id}&name=${name}`)
        },

        getSettings() {
            return this.axios.get('/settings')
        },

        updateSetting(key, description, value) {
            const data = {key: key}

            if (description)
                data['description'] = description
            else if (value)
                data['value'] = value

            if (Object.keys(data).length > 1)
                return this.axios.put('/settings', data)
            else
                return null
        },

        sendMail(userId, text) {
            return this.axios.post('/users/sendMail', {
                id: userId,
                text: text
            })
        }
    }
}