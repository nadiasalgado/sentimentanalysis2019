import { ISearch } from 'app/shared/model/search.model';

export const enum Sexoption {
    FEMALE = 'FEMALE',
    MALE = 'MALE',
    OTHER = 'OTHER'
}

export const enum Countrylist {
    ARGENTINA = 'ARGENTINA',
    ALGERIA = 'ALGERIA',
    ANGOLA = 'ANGOLA',
    BELGIUM = 'BELGIUM',
    BOTSWANA = 'BOTSWANA',
    BRAZIL = 'BRAZIL',
    CAMEROON = 'CAMEROON',
    CANADA = 'CANADA',
    CHINA = 'CHINA',
    EGYPYT = 'EGYPYT',
    ETHOPIA = 'ETHOPIA',
    FRANCE = 'FRANCE',
    GABON = 'GABON',
    GERMANY = 'GERMANY',
    GHANA = 'GHANA',
    ISRAEL = 'ISRAEL',
    ITALY = 'ITALY',
    JAMAICA = 'JAMAICA',
    JAPAN = 'JAPAN',
    KENYA = 'KENYA',
    LESOTHO = 'LESOTHO',
    LIBERIA = 'LIBERIA',
    LIBYA = 'LIBYA',
    MALI = 'MALI',
    MAURITIUS = 'MAURITIUS',
    MEXICO = 'MEXICO',
    MOROCCO = 'MOROCCO',
    MOZAMBIQUE = 'MOZAMBIQUE',
    NAMIBIA = 'NAMIBIA',
    NETHERLANDS = 'NETHERLANDS',
    NIGER = 'NIGER',
    NIGERIA = 'NIGERIA',
    PARAGUAY = 'PARAGUAY',
    PHILIPPINES = 'PHILIPPINES',
    PORTUGAL = 'PORTUGAL',
    RUSSIA = 'RUSSIA',
    RWANDA = 'RWANDA',
    SAUDI_ARABIA = 'SAUDI_ARABIA',
    SENEGAL = 'SENEGAL',
    SOMALIA = 'SOMALIA',
    SOUTH_AFRICA = 'SOUTH_AFRICA',
    SPAIN = 'SPAIN',
    SWAZILAND = 'SWAZILAND',
    SWEDEN = 'SWEDEN',
    SWITZERLAND = 'SWITZERLAND',
    TANZANIA = 'TANZANIA',
    TUNISIA = 'TUNISIA',
    UGANDA = 'UGANDA',
    UNITED_KINGDOM = 'UNITED_KINGDOM',
    VENEZUAL = 'VENEZUAL',
    YEMEN = 'YEMEN',
    ZAMBIA = 'ZAMBIA',
    ZIMBWAWE = 'ZIMBWAWE'
}

export interface IUserProfile {
    id?: number;
    firstName?: string;
    lastName?: string;
    userName?: string;
    age?: number;
    email?: string;
    sex?: Sexoption;
    phone?: number;
    organization?: string;
    country?: Countrylist;
    usersearches?: ISearch[];
}

export class UserProfile implements IUserProfile {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public userName?: string,
        public age?: number,
        public email?: string,
        public sex?: Sexoption,
        public phone?: number,
        public organization?: string,
        public country?: Countrylist,
        public usersearches?: ISearch[]
    ) {}
}
