import { ParametroModule } from './parametro.module';

describe('ParametroModule', () => {
  let parametroModule: ParametroModule;

  beforeEach(() => {
    parametroModule = new ParametroModule();
  });

  it('should create an instance', () => {
    expect(parametroModule).toBeTruthy();
  });
});
