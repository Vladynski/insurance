export function checkPhoneInputUpdate(event) {
    let resultPhone = ''
    const backspace = event.key === 'Backspace'
    const length = event.target.value.length


    if ((event.target.value === '+375 ' && event.key === 'Backspace') || !event.key.match(/\d|F5|Backspace/g))
        event.target.event = event.preventDefault()
    else if (!backspace) {
        if (!event.target.value.startsWith('+375'))
            event.target.value = '+375 '

        if (length === 7)
            event.target.value += ' '
        else if (length === 11 || length === 14)
            event.target.value += '-'
    }

    return resultPhone
}

export function checkNumbersInputUpdate(event) {
    if ('1234567890'.indexOf(event.key) === -1 && event.key !== 'Backspace')
        event.preventDefault()
}

export function getFormatPhoneNumber(phoneNumber) {
    if (!phoneNumber)
        return '+375 '
    return '+' + phoneNumber.substring(0, 3) + ' ' + phoneNumber.substring(3, 5) + ' ' + phoneNumber.substring(5, 8) + '-' + phoneNumber.substring(8, 10) + '-' + phoneNumber.substring(10, 12)
}

export function removeSpaces(text) {
    return text.replace(/\s/g, '')
}

export function clearPhoneNumber(phoneNumber) {
    if (phoneNumber)
        return phoneNumber.replace(/\D/g, '')
    else
        return undefined
}

export function clearRegistrationNumber(registrationNumber) {
    return saveOnlyNumsAndUpperLetters(registrationNumber)
}

export function clearWinNumber(winNumber) {
    return saveOnlyNumsAndUpperLetters(winNumber)
}

function saveOnlyNumsAndUpperLetters(text) {
    return text.toUpperCase().replace(/[^0-9A-Z]/g, '')
}

export function div(dividend, divider) {
    return Math.floor(dividend / divider)
}

export function randomId() {
    return Math.floor(Math.random() * 1000)
}

// export function encodeImageFileAsURL(element, callback) {
//     const file = element.target.value;
//     const reader = new FileReader();
//     reader.onload = function() {
//         alert(reader.result)
//         callback(reader.result)
//     }
//     reader.readAsDataURL(file);
// }