using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CurrentPowerUpScript : MonoBehaviour {

    public GameObject playerKnight;

    public Sprite blankSprite;

    public Sprite superSprite;
    public Sprite sonicSprite;
    public Sprite chickenSprite;
    public Sprite invisSprite;
    public Sprite rapidSprite;
    public Sprite shieldSprite;
    public Sprite vampireSprite;
    public Sprite fireSprite;
    public Sprite drunkSprite;
    public Sprite doubleSprite;
    public Sprite babySprite;

    // Use this for initialization
    void Start() {

    }

    // Update is called once per frame
    void Update() {
        GetComponent<Image>().enabled = true;
        if (playerKnight != null) {
            switch (playerKnight.GetComponent<CharacterControler>().currentPower) {
                case CharacterControler.PowerUp.SUPER_SPEED:
                    GetComponent<Image>().sprite = superSprite;
                    break;
                case CharacterControler.PowerUp.SONIC:
                    GetComponent<Image>().sprite = sonicSprite;
                    break;
                case CharacterControler.PowerUp.CHICKEN:
                    GetComponent<Image>().sprite = chickenSprite;
                    break;
                case CharacterControler.PowerUp.INVISIBLE:
                    GetComponent<Image>().sprite = invisSprite;
                    break;
                case CharacterControler.PowerUp.RAPID_FIRE:
                    GetComponent<Image>().sprite = rapidSprite;
                    break;
                case CharacterControler.PowerUp.SHIELD:
                    GetComponent<Image>().sprite = shieldSprite;
                    break;
                case CharacterControler.PowerUp.VAMPIRE:
                    GetComponent<Image>().sprite = vampireSprite;
                    break;
                case CharacterControler.PowerUp.FIRESHOES:
                    GetComponent<Image>().sprite = fireSprite;
                    break;
                case CharacterControler.PowerUp.GOLDEN_DRUNK:
                    GetComponent<Image>().sprite = drunkSprite;
                    break;
                case CharacterControler.PowerUp.DOUBLE:
                    GetComponent<Image>().sprite = doubleSprite;
                    break;
                case CharacterControler.PowerUp.BABY:
                    GetComponent<Image>().sprite = babySprite;
                    break;
                default:
                    GetComponent<Image>().sprite = blankSprite;
                    break;
            }
            
        } else {
            GetComponent<Image>().sprite = blankSprite;
        }
    }
}
