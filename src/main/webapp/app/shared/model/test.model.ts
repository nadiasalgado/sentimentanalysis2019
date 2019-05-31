export interface ITest {
    type?: string;
    VposTxt?: string;
    posTxt?: string;
    neuTxt?: string;
    negTxt?: string;
    VnegTxt?: string;
    statText?: string;
    num?: number;
    VposCount?: number;
    posCount?: number;
    neuCount?: number;
    negCount?: number;
    VnegCount?: number;
}

export class Test implements ITest {
    constructor(
        public type?: string,
        public VposTxt?: string,
        public posTxt?: string,
        public neuTxt?: string,
        public negTxt?: string,
        public VnegTxt?: string,
        public statText?: string,
        public num?: number,
        public VposCount?: number,
        public posCount?: number,
        public neuCount?: number,
        public negCount?: number,
        public VnegCount?: number
    ) {}
}
