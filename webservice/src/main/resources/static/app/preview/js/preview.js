'use strict';

var isoCountries = {
	'AF' : 'Afghanistan',
	'AX' : 'Aland Islands',
	'AL' : 'Albania',
	'DZ' : 'Algeria',
	'AS' : 'American Samoa',
	'AD' : 'Andorra',
	'AO' : 'Angola',
	'AI' : 'Anguilla',
	'AQ' : 'Antarctica',
	'AG' : 'Antigua And Barbuda',
	'AR' : 'Argentina',
	'AM' : 'Armenia',
	'AW' : 'Aruba',
	'AU' : 'Australia',
	'AT' : 'Austria',
	'AZ' : 'Azerbaijan',
	'BS' : 'Bahamas',
	'BH' : 'Bahrain',
	'BD' : 'Bangladesh',
	'BB' : 'Barbados',
	'BY' : 'Belarus',
	'BE' : 'Belgium',
	'BZ' : 'Belize',
	'BJ' : 'Benin',
	'BM' : 'Bermuda',
	'BT' : 'Bhutan',
	'BO' : 'Bolivia',
	'BA' : 'Bosnia And Herzegovina',
	'BW' : 'Botswana',
	'BV' : 'Bouvet Island',
	'BR' : 'Brazil',
	'IO' : 'British Indian Ocean Territory',
	'BN' : 'Brunei Darussalam',
	'BG' : 'Bulgaria',
	'BF' : 'Burkina Faso',
	'BI' : 'Burundi',
	'KH' : 'Cambodia',
	'CM' : 'Cameroon',
	'CA' : 'Canada',
	'CV' : 'Cape Verde',
	'KY' : 'Cayman Islands',
	'CF' : 'Central African Republic',
	'TD' : 'Chad',
	'CL' : 'Chile',
	'CN' : 'China',
	'CX' : 'Christmas Island',
	'CC' : 'Cocos (Keeling) Islands',
	'CO' : 'Colombia',
	'KM' : 'Comoros',
	'CG' : 'Congo',
	'CD' : 'Congo, Democratic Republic',
	'CK' : 'Cook Islands',
	'CR' : 'Costa Rica',
	'CI' : 'Cote D\'Ivoire',
	'HR' : 'Croatia',
	'CU' : 'Cuba',
	'CY' : 'Cyprus',
	'CZ' : 'Czech Republic',
	'DK' : 'Denmark',
	'DJ' : 'Djibouti',
	'DM' : 'Dominica',
	'DO' : 'Dominican Republic',
	'EC' : 'Ecuador',
	'EG' : 'Egypt',
	'SV' : 'El Salvador',
	'GQ' : 'Equatorial Guinea',
	'ER' : 'Eritrea',
	'EE' : 'Estonia',
	'ET' : 'Ethiopia',
	'FK' : 'Falkland Islands (Malvinas)',
	'FO' : 'Faroe Islands',
	'FJ' : 'Fiji',
	'FI' : 'Finland',
	'FR' : 'France',
	'GF' : 'French Guiana',
	'PF' : 'French Polynesia',
	'TF' : 'French Southern Territories',
	'GA' : 'Gabon',
	'GM' : 'Gambia',
	'GE' : 'Georgia',
	'DE' : 'Germany',
	'GH' : 'Ghana',
	'GI' : 'Gibraltar',
	'GR' : 'Greece',
	'GL' : 'Greenland',
	'GD' : 'Grenada',
	'GP' : 'Guadeloupe',
	'GU' : 'Guam',
	'GT' : 'Guatemala',
	'GG' : 'Guernsey',
	'GN' : 'Guinea',
	'GW' : 'Guinea-Bissau',
	'GY' : 'Guyana',
	'HT' : 'Haiti',
	'HM' : 'Heard Island & Mcdonald Islands',
	'VA' : 'Holy See (Vatican City State)',
	'HN' : 'Honduras',
	'HK' : 'Hong Kong',
	'HU' : 'Hungary',
	'IS' : 'Iceland',
	'IN' : 'India',
	'ID' : 'Indonesia',
	'IR' : 'Iran, Islamic Republic Of',
	'IQ' : 'Iraq',
	'IE' : 'Ireland',
	'IM' : 'Isle Of Man',
	'IL' : 'Israel',
	'IT' : 'Italy',
	'JM' : 'Jamaica',
	'JP' : 'Japan',
	'JE' : 'Jersey',
	'JO' : 'Jordan',
	'KZ' : 'Kazakhstan',
	'KE' : 'Kenya',
	'KI' : 'Kiribati',
	'KR' : 'Korea',
	'KW' : 'Kuwait',
	'KG' : 'Kyrgyzstan',
	'LA' : 'Lao People\'s Democratic Republic',
	'LV' : 'Latvia',
	'LB' : 'Lebanon',
	'LS' : 'Lesotho',
	'LR' : 'Liberia',
	'LY' : 'Libyan Arab Jamahiriya',
	'LI' : 'Liechtenstein',
	'LT' : 'Lithuania',
	'LU' : 'Luxembourg',
	'MO' : 'Macao',
	'MK' : 'Macedonia',
	'MG' : 'Madagascar',
	'MW' : 'Malawi',
	'MY' : 'Malaysia',
	'MV' : 'Maldives',
	'ML' : 'Mali',
	'MT' : 'Malta',
	'MH' : 'Marshall Islands',
	'MQ' : 'Martinique',
	'MR' : 'Mauritania',
	'MU' : 'Mauritius',
	'YT' : 'Mayotte',
	'MX' : 'Mexico',
	'FM' : 'Micronesia, Federated States Of',
	'MD' : 'Moldova',
	'MC' : 'Monaco',
	'MN' : 'Mongolia',
	'ME' : 'Montenegro',
	'MS' : 'Montserrat',
	'MA' : 'Morocco',
	'MZ' : 'Mozambique',
	'MM' : 'Myanmar',
	'NA' : 'Namibia',
	'NR' : 'Nauru',
	'NP' : 'Nepal',
	'NL' : 'Netherlands',
	'AN' : 'Netherlands Antilles',
	'NC' : 'New Caledonia',
	'NZ' : 'New Zealand',
	'NI' : 'Nicaragua',
	'NE' : 'Niger',
	'NG' : 'Nigeria',
	'NU' : 'Niue',
	'NF' : 'Norfolk Island',
	'MP' : 'Northern Mariana Islands',
	'NO' : 'Norway',
	'OM' : 'Oman',
	'PK' : 'Pakistan',
	'PW' : 'Palau',
	'PS' : 'Palestinian Territory, Occupied',
	'PA' : 'Panama',
	'PG' : 'Papua New Guinea',
	'PY' : 'Paraguay',
	'PE' : 'Peru',
	'PH' : 'Philippines',
	'PN' : 'Pitcairn',
	'PL' : 'Poland',
	'PT' : 'Portugal',
	'PR' : 'Puerto Rico',
	'QA' : 'Qatar',
	'RE' : 'Reunion',
	'RO' : 'Romania',
	'RU' : 'Russian Federation',
	'RW' : 'Rwanda',
	'BL' : 'Saint Barthelemy',
	'SH' : 'Saint Helena',
	'KN' : 'Saint Kitts And Nevis',
	'LC' : 'Saint Lucia',
	'MF' : 'Saint Martin',
	'PM' : 'Saint Pierre And Miquelon',
	'VC' : 'Saint Vincent And Grenadines',
	'WS' : 'Samoa',
	'SM' : 'San Marino',
	'ST' : 'Sao Tome And Principe',
	'SA' : 'Saudi Arabia',
	'SN' : 'Senegal',
	'RS' : 'Serbia',
	'SC' : 'Seychelles',
	'SL' : 'Sierra Leone',
	'SG' : 'Singapore',
	'SK' : 'Slovakia',
	'SI' : 'Slovenia',
	'SB' : 'Solomon Islands',
	'SO' : 'Somalia',
	'ZA' : 'South Africa',
	'GS' : 'South Georgia And Sandwich Isl.',
	'ES' : 'Spain',
	'LK' : 'Sri Lanka',
	'SD' : 'Sudan',
	'SR' : 'Suriname',
	'SJ' : 'Svalbard And Jan Mayen',
	'SZ' : 'Swaziland',
	'SE' : 'Sweden',
	'CH' : 'Switzerland',
	'SY' : 'Syrian Arab Republic',
	'TW' : 'Taiwan',
	'TJ' : 'Tajikistan',
	'TZ' : 'Tanzania',
	'TH' : 'Thailand',
	'TL' : 'Timor-Leste',
	'TG' : 'Togo',
	'TK' : 'Tokelau',
	'TO' : 'Tonga',
	'TT' : 'Trinidad And Tobago',
	'TN' : 'Tunisia',
	'TR' : 'Turkey',
	'TM' : 'Turkmenistan',
	'TC' : 'Turks And Caicos Islands',
	'TV' : 'Tuvalu',
	'UG' : 'Uganda',
	'UA' : 'Ukraine',
	'AE' : 'United Arab Emirates',
	'GB' : 'United Kingdom',
	'US' : 'United States',
	'UM' : 'United States Outlying Islands',
	'UY' : 'Uruguay',
	'UZ' : 'Uzbekistan',
	'VU' : 'Vanuatu',
	'VE' : 'Venezuela',
	'VN' : 'Viet Nam',
	'VG' : 'Virgin Islands, British',
	'VI' : 'Virgin Islands, U.S.',
	'WF' : 'Wallis And Futuna',
	'EH' : 'Western Sahara',
	'YE' : 'Yemen',
	'ZM' : 'Zambia',
	'ZW' : 'Zimbabwe'
};
var isoLanguageNames = {
	aa : "Afar",
	ab : "Abkhazian",
	ae : "Avestan",
	af : "Afrikaans",
	ak : "Akan",
	am : "Amharic",
	an : "Aragonese",
	ar : "Arabic",
	as : "Assamese",
	av : "Avaric",
	ay : "Aymara",
	az : "Azerbaijani",

	ba : "Bashkir",
	be : "Belarusian",
	bg : "Bulgarian",
	bh : "Bihari",
	bi : "Bislama",
	bm : "Bambara",
	bn : "Bengali",
	bo : "Tibetan",
	br : "Breton",
	bs : "Bosnian",

	ca : "Catalan",
	ce : "Chechen",
	ch : "Chamorro",
	co : "Corsican",
	cr : "Cree",
	cs : "Czech",
	cu : "Church Slavic",
	cv : "Chuvash",
	cy : "Welsh",

	da : "Danish",
	de : "German",
	dv : "Divehi",
	dz : "Dzongkha",

	ee : "Ewe",
	el : "Greek",
	en : "English",
	eo : "Esperanto",
	es : "Spanish",
	et : "Estonian",
	eu : "Basque",

	fa : "Persian",
	ff : "Fulah",
	fi : "Finnish",
	fj : "Fijian",
	fo : "Faroese",
	fr : "French",
	fy : "Western Frisian",

	ga : "Irish",
	gd : "Gaelic",
	gl : "Galician",
	gn : "Guarani",
	gu : "Gujarati",
	gv : "Manx",

	ha : "Hausa",
	he : "Hebrew",
	hi : "Hindi",
	ho : "Hiri Motu",
	hr : "Croatian",
	ht : "Haitian",
	hu : "Hungarian",
	hy : "Armenian",
	hz : "Herero",

	ia : "Interlingua (International Auxiliary Language Association)",
	id : "Indonesian",
	ie : "Interlingue",
	ig : "Igbo",
	ii : "Sichuan Yi",
	ik : "Inupiaq",
	io : "Ido",
	is : "Icelandic",
	it : "Italian",
	iu : "Inuktitut",

	ja : "Japanese",
	jv : "Javanese",

	ka : "Georgian",
	kg : "Kongo",
	ki : "Kikuyu",
	kj : "Kuanyama",
	kk : "Kazakh",
	kl : "Kalaallisut",
	km : "Central Khmer",
	kn : "Kannada",
	ko : "Korean",
	kr : "Kanuri",
	ks : "Kashmiri",
	ku : "Kurdish",
	kv : "Komi",
	kw : "Cornish",
	ky : "Kirghiz",

	la : "Latin",
	lb : "Luxembourgish",
	lg : "Ganda",
	li : "Limburgan",
	ln : "Lingala",
	lo : "Lao",
	lt : "Lithuanian",
	lu : "Luba-Katanga",
	lv : "Latvian",

	mg : "Malagasy",
	mh : "Marshallese",
	mi : "Maori",
	mk : "Macedonian",
	ml : "Malayalam",
	mn : "Mongolian",
	mr : "Marathi",
	ms : "Malay",
	mt : "Maltese",
	my : "Burmese",

	na : "Nauru",
	nb : "Norwegian Bokmål",
	nd : "North Ndebele",
	ne : "Nepali",
	ng : "Ndonga",
	nl : "Dutch",
	nn : "Norwegian",
	no : "Norwegian",
	nr : "South Ndebele",
	nv : "Navajo",
	ny : "Chichewa",

	oc : "Occitan",
	oj : "Ojibwa",
	om : "Oromo",
	or : "Oriya",
	os : "Ossetian",

	pa : "Panjabi",
	pi : "Pali",
	pl : "Polish",
	ps : "Pushto",
	pt : "Portuguese",

	qu : "Quechua",

	rm : "Romansh",
	rn : "Rundi",
	ro : "Romanian",
	ru : "Russian",
	rw : "Kinyarwanda",

	sa : "Sanskrit",
	sc : "Sardinian",
	sd : "Sindhi",
	se : "Northern Sami",
	sg : "Sango",
	si : "Sinhala",
	sk : "Slovak",
	sl : "Slovenian",
	sm : "Samoan",
	sn : "Shona",
	so : "Somali",
	sq : "Albanian",
	sr : "Serbian",
	ss : "Swati",
	st : "Southern Sotho",
	su : "Sundanese",
	sv : "Swedish",
	sw : "Swahili",

	ta : "Tamil",
	te : "Telugu",
	tg : "Tajik",
	th : "Thai",
	ti : "Tigrinya",
	tk : "Turkmen",
	tl : "Tagalog",
	tn : "Tswana",
	to : "Tonga (Tonga Islands)",
	tr : "Turkish",
	ts : "Tsonga",
	tt : "Tatar",
	tw : "Twi",
	ty : "Tahitian",

	ug : "Uighur",
	uk : "Ukrainian",
	ur : "Urdu",
	uz : "Uzbek",

	ve : "Venda",
	vi : "Vietnamese",
	vo : "Volapük",

	wa : "Walloon",
	wo : "Wolof",

	xh : "Xhosa",

	yi : "Yiddish",
	yo : "Yoruba",

	za : "Zhuang",
	zh : "Chinese",
	zu : "Zulu"
};

var odkCommonProperties = {};

function getCountryName(countryCode) {
	if (isoCountries.hasOwnProperty(countryCode)) {
		return isoCountries[countryCode];
	} else {
		return countryCode;
	}
}

function getLanguageName(languageCode) {
	if (isoLanguageNames.hasOwnProperty(languageCode)) {
		return isoLanguageNames[languageCode];
	} else {
		return languageCode;
	}
}
var pageStack = [];
var odkOrientation = 'horizontal';
function sizeIframe() {
	var screenchoice = $('#screenchoice');
	var value = screenchoice[0].value;
	var parts = value.substring(1).split('x');
	var $el = $('#previewscreen');
	var desiredWidth = parseInt(parts[0]);
	var desiredHeight = parseInt(parts[1]);
	$el.width(desiredWidth);
	var internalHeight = desiredHeight + 1;
	$el.height(internalHeight); // we somehow lose a pixel?
	var pvs = document.getElementById('previewscreen');
	if (pvs.tagName === 'DIV') {
		pvs = pvs.getElementsByClassName("detailHalfscreen")[0];
	}
	var cw = pvs.contentWindow;
	var $bland = $('#previewblandviewport');
	var top = $bland.offset().top;
	internalHeight = top + 20 + internalHeight + 40 + 30;
	if (internalHeight < 1600) {
		internalHeight = 1600;
	}
	$bland.height(internalHeight - $bland.offset().top - 20 - 40 - 30);
	if (desiredWidth > desiredHeight) {
		odkOrientation = 'horizontal';
	} else {
		odkOrientation = 'vertical';
	}
	$('#detailHalfscreen').removeClass('horizontal vertical').addClass(
			odkOrientation);
	$('#sublistHalfscreen').removeClass('horizontal vertical').addClass(
			odkOrientation);
	return cw;
}
function resizeIframe() {
	var cw = sizeIframe();
	cw.location.reload();
}
/**
 * Compute and return the base URI for this machine. This will allow the code to
 * function independently of the host name.
 * 
 * Returns a string representing the base uri in the format:
 * http://DOMAIN/DIRS/. Note the trailing slash.
 */
function _computeBaseOpendatakitPath() {
	// To compute this we are going to rely on the location of the containing
	// file relative to the location we are serving as root. If this is
	// changed, this file must be updated appropriately.
	// Since we are expecting this file to live in app/index.html, we
	// can look for the first occurrence and take everything before it.
	var expectedFileLocation = 'app/preview/preview.html';
	var fileLocation = window.location.href;
	var indexToFile = fileLocation.indexOf(expectedFileLocation);
	var result = fileLocation.substring(0, indexToFile);
	return result + "app/opendatakit/";
}

function getPlatformInfo(baseUri, logLevel) {
	var container;
	var version = window.navigator.appVersion.split('Chrome/')[1];
	if (version !== undefined) {
		container = 'Chrome';
		version = version.split(' ')[0];
	} else {
		container = 'Unknown';
		version = 'unknown';
	}
	var language = window.navigator.languages[0];
	var lang;
	var langName;
	var country;
	var countryName;
	var idash = language.indexOf('-');
	if (idash !== -1) {
		lang = language.substring(0, idash);
		country = language.substring(idash + 1);
	} else {
		lang = language;
		country = 'US';
	}
	langName = getLanguageName(lang);
	countryName = getCountryName(country);
	var platformInfo = {
		container : container,
		version : version,
		appName : getAppName(),
		baseUri : baseUri,
		formsUri : "content://org.opendatakit.provider.forms/",
		activeUser : getActiveUser(),
		logLevel : logLevel,
		preferredLocale : lang + '_' + country,
		// true only if user did not override the device locale
		usingDeviceLocale : true,
		// info about the device locale:
		isoCountry : country,
		displayCountry : countryName,
		isoLanguage : lang,
		displayLanguage : langName
	};
	// Because the phone returns a String, we too are going to return a
	// string here.
	var result = JSON.stringify(platformInfo);
	return result;
}
function getAppName() {
	return odkCommonProperties.appName;
}
function getActiveUser() {
	return odkCommonProperties.activeUser;
}
function getServerUrl() {
	return odkCommonProperties.serverUrl;
}
function getProperty(propertyName) {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkCommonHostIf/property/' + propertyName,
			false); // `false` makes the request synchronous
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.send(null);

	var propertyValue = null;
	if (xhr.status === 200) {
		propertyValue = JSON.parse(xhr.responseText);
	}
	return propertyValue;
}
function getSessionVariables() {
	var ps = document.getElementById('previewscreen');
	return ps.odkSessionVariables;
}
function getQueuedActions() {
	var ps = document.getElementById('previewscreen');
	return ps.odkQueuedActions;
}
function getSurveyStateMgmt() {
	var ps = document.getElementById('previewscreen');
	return ps.odkSurveyStateMgmt;
}
function getViewDataParameters(odkViewId) {
	var ps = document.getElementById('previewscreen');
	var struct = ps.odkParserStruct;
	if (odkViewId !== "sublist") {
		return struct;
	} else {
		return ps.odkSubListStruct;
	}
	return null;
}
function odkDoAction(dispatchStruct, action, intent, contentLocation) {
	var ps = document.getElementById('previewscreen');
	if (dispatchStruct === null) {
		ps.odkDispatchStruct = null;
		ps.odkAction = null;
	} else {
		ps.odkDispatchStruct = dispatchStruct;
		ps.odkAction = action;
	}
	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/webservice/OdkIntentsHostIf', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Content-Type', 'application/json;charset=utf-8');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		pushPageAndOpen(responseStruct.url, intent, responseStruct);
	};
	xhr.send(JSON.stringify({
		action : action,
		intent : intent,
		url : contentLocation
	}));
}
function setSubListView(action, intent, contentLocation) {
	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/webservice/OdkIntentsHostIf', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Content-Type', 'application/json;charset=utf-8');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		var pvs = document.getElementById('previewscreen');
		pvs.odkSubListStruct = responseStruct;
		if (pvs.tagName === 'DIV') {
			pvs = pvs.getElementsByClassName("sublistHalfscreen")[0];
		}
		var cw = pvs.contentWindow;
		var url = responseStruct.url;
		setTimeout(function() {
			cw.location.href = url;
		}, 5);
	};
	xhr.send(JSON.stringify({
		action : action,
		intent : intent,
		url : contentLocation
	}));
}
function odkCloseWindowAction(resultCode, keyValueBundle) {
	// this should remove the current iframe and raise the prior one to the
	// foreground
	if (pageStack.length > 0) {
		var priorId = pageStack.pop();
		var priorPS = document.getElementById('previewscreen-'
				+ pageStack.length);
		var ps = document.getElementById('previewscreen');
		var parent = ps.parentNode;
		priorPS.style.display = 'block';
		parent.removeChild(ps);
		priorPS.id = 'previewscreen';
		if (priorPS.odkDispatchStruct !== null) {
			priorPS.odkQueuedActions.push(JSON.stringify({
				dispatchStruct : priorPS.odkDispatchStruct,
				action : priorPS.odkAction,
				jsonValue : {
					status : resultCode,
					result : keyValueBundle
				}
			}));
			priorPS.odkDispatchStruct = null;
			priorPS.odkAction = null;
		}
		var cw = sizeIframe();
		cw.location.reload(false);
	}
}
function pushPageAndOpen(value, intent, parserStruct) {
	var ps = document.getElementById('previewscreen');
	var parent = ps.parentNode;
	ps.id = 'previewscreen-' + pageStack.length;
	pageStack.push(ps.id);
	ps.style.display = 'none';
	var tableDisplayViewType = parserStruct.tableDisplayViewType;
	var newIframe;
	if (tableDisplayViewType === 'DETAIL_WITH_LIST') {
		var div = document.createElement('div');
		div.className = 'resizeablescreen';
		div.setAttribute('name', 'screen');
		div.id = 'previewscreen';
		// synthesize
		div.odkIntent = intent;
		div.odkParserStruct = parserStruct;
		div.odkSubListStruct = {};
		//
		div.odkSessionVariables = {};
		div.odkQueuedActions = [];
		div.odkSurveyStateMgmt = {};
		div.odkDispatchStruct = null;
		div.odkAction = null;

		parent.appendChild(div);

		newIframe = document.createElement('iframe');
		newIframe.odkViewId = 'detail';
		newIframe.className = "detailHalfscreen " + odkOrientation;
		//
		div.appendChild(newIframe);

		var newIframe2 = document.createElement('iframe');
		newIframe2.odkViewId = 'sublist';
		newIframe2.className = "sublistHalfscreen " + odkOrientation;
		//
		div.appendChild(newIframe2);

		newIframe.src = parserStruct.url;
	} else {
		newIframe = document.createElement('iframe');
		newIframe.className = 'resizeablescreen';
		newIframe.setAttribute('name', 'screen');
		newIframe.id = 'previewscreen';
		newIframe.odkIntent = intent;
		newIframe.odkParserStruct = parserStruct;
		newIframe.odkSubListStruct = {};
		newIframe.odkSessionVariables = {};
		newIframe.odkQueuedActions = [];
		newIframe.odkSurveyStateMgmt = {};
		newIframe.odkDispatchStruct = null;
		newIframe.odkAction = null;
		parent.appendChild(newIframe);
		newIframe.src = value;
	}
	sizeIframe();
}
function closeAndPopPage() {
	// this should remove the current iframe and raise the prior one to the
	// foreground
	if (pageStack.length > 0) {
		var priorId = pageStack.pop();
		var priorPS = document.getElementById('previewscreen-'
				+ pageStack.length);
		var ps = document.getElementById('previewscreen');
		var parent = ps.parentNode;
		priorPS.style.display = 'block';
		parent.removeChild(ps);
		priorPS.id = 'previewscreen';
		var cw = sizeIframe();
		cw.location.reload(false);
	}
}
function updateUserInformation() {
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Updating user information ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkCommonHostIf/common', false); // `false`
																	// makes the
																	// request
																	// synchronous
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.send(null);

	if (xhr.status === 200) {
		odkCommonProperties = JSON.parse(xhr.responseText);
	} else {
		odkCommonProperties = {
			appName : "default",
			activeUser : "anonymous",
			rolesList : "",
			defaultGroup : "",
			usersList : "",
			serverUrl : "https://opendatakit-tablesdemo.appspot.com"
		};
	}
	$('#previewblandviewport').waitMe('hide');
}
function initialLoadPage() {
	updateUserInformation();
	$("#cfgserverurl").html(getServerUrl());
	$("#cfgappname").html(getAppName());
	$("#cfgactiveuser").html(getActiveUser());
	var parent = document.getElementById('previewblandviewport');
	var newIframe = document.createElement('iframe');
	newIframe.className = 'resizeablescreen';
	newIframe.setAttribute('name', 'screen');
	newIframe.id = 'previewscreen';
	// synthesize
	newIframe.odkIntent = {
		extras : {
			filename : "config/assets/index.html"
		}
	};
	newIframe.odkParserStruct = {
		tool : "tables",
		url : _computeBaseOpendatakitPath() + getAppName()
				+ "/config/assets/index.html",
		appName : getAppName()
	};
	newIframe.odkSubListStruct = {};
	//
	newIframe.odkSessionVariables = {};
	newIframe.odkQueuedActions = [];
	newIframe.odkSurveyStateMgmt = {};
	newIframe.odkDispatchStruct = null;
	newIframe.odkAction = null;
	parent.appendChild(newIframe);
	var cw = sizeIframe();
    newIframe.src = _computeBaseOpendatakitPath() + getAppName()
			+ "/config/assets/index.html";
}
function backbutton() {
	// TODO: allow implementation to catch and confirm exit (e.g., survey)
	// treat this as a cancel
	odkCloseWindowAction(0, {});
}
function cleanbutton() {
	// abort all nested windows
	while (pageStack.length > 0) {
		// cancel this window
		odkCloseWindowAction(0, {});
	}
	// clear the top-level window
	var parent = document.getElementById('previewblandviewport');
	parent.innerHTML = "Scorching the Earth ...";
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Scorching the Earth ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkClean', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		var parent = document.getElementById('previewblandviewport');
		parent.innerHTML = "";
		$('#previewblandviewport').waitMe('hide');
		initialLoadPage();
	};
	xhr.send();
}
function samplesbutton() {
	// abort all nested windows
	while (pageStack.length > 0) {
		// cancel this window
		odkCloseWindowAction(0, {});
	}
	// clear the top-level window
	var parent = document.getElementById('previewblandviewport');
	parent.innerHTML = "Overwriting with Application Samples ...";
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Overwriting with Application Samples ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkSamples', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		var parent = document.getElementById('previewblandviewport');
		parent.innerHTML = "";
		$('#previewblandviewport').waitMe('hide');
		initialLoadPage();
	};
	xhr.send();
}
function reinitbutton() {
	// abort all nested windows
	while (pageStack.length > 0) {
		// cancel this window
		odkCloseWindowAction(0, {});
	}
	// clear the top-level window
	var parent = document.getElementById('previewblandviewport');
	parent.innerHTML = "Re-initializing ...";
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Re-initializing ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkReInitialize', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		var parent = document.getElementById('previewblandviewport');
		parent.innerHTML = "";
		$('#previewblandviewport').waitMe('hide');
		initialLoadPage();
	};
	xhr.send();
}

function updateParamsButton() {
    updateUserInformation();
	$("#cfgserverurl").html(getServerUrl());
	$("#cfgappname").html(getAppName());
	$("#cfgactiveuser").html(getActiveUser());
}

function loginButton() {
	// abort all nested windows
	while (pageStack.length > 0) {
		// cancel this window
		odkCloseWindowAction(0, {});
	}
	// clear the top-level window
	var parent = document.getElementById('previewblandviewport');
	parent.innerHTML = "Verifying User Permissions Against Server ...";
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Verifying User Permissions Against Server ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	// resolve filename - asynchronous
    var data = {};
    data['username'] = 'sue';
    data['password'] ='password';
    data['appName'] = 'default';
    data['scratchDir'] = '';
    data['serverUrl'] = 'http://128.208.4.152';

	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/webservice/OdkVerifySettingsFromAppServer', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		//var responseStruct = JSON.parse(this.responseText);
		var parent = document.getElementById('previewblandviewport');
		parent.innerHTML = "";
		$('#previewblandviewport').waitMe('hide');
	};
	xhr.send(JSON.stringify(data));
}

function syncbutton() {
	// abort all nested windows
	while (pageStack.length > 0) {
		// cancel this window
		odkCloseWindowAction(0, {});
	}
	// clear the top-level window
	var parent = document.getElementById('previewblandviewport');
	parent.innerHTML = "Syncing From Server ...";
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Syncing From Server ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkSyncFromAppServer', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		var parent = document.getElementById('previewblandviewport');
		parent.innerHTML = "";
		$('#previewblandviewport').waitMe('hide');
		initialLoadPage();
	};
	xhr.send();
}
function resetserverbutton() {
	// abort all nested windows

	while (pageStack.length > 0) {
		// cancel this window
		odkCloseWindowAction(0, {});
	}
	// clear the top-level window
	var parent = document.getElementById('previewblandviewport');
	parent.innerHTML = "Resetting Server...";
	$('#previewblandviewport').waitMe({
		effect : 'roundBounce',
		text : 'Resetting Server ...',
		bg : 'rgba(255,255,255,0.7)',
		color : '#000',
		sizeW : '',
		sizeH : ''
	});

	// resolve filename - asynchronous
	var xhr = new XMLHttpRequest();
	xhr.open('GET', '/webservice/OdkResetAppServer', true);
	xhr.setRequestHeader('X-OpenDataKit-Version', '2.0');
	xhr.setRequestHeader('Accept', 'application/json;charset=utf-8');
	xhr.onload = function() {
		// TODO: handle errors
		var responseStruct = JSON.parse(this.responseText);
		var parent = document.getElementById('previewblandviewport');
		parent.innerHTML = "";
		$('#previewblandviewport').waitMe('hide');
		initialLoadPage();
	};
	xhr.send();
}
