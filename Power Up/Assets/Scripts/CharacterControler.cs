using System.Collections.Generic;
using UnityEngine;
using TeamUtility.IO;

public class CharacterControler : MonoBehaviour {

    [SerializeField]
    private PlayerID player;

    //Sound Effects
    private AudioSource source; 

    public AudioClip screamInPain;
    public AudioClip grunt;

    public AudioClip cluck;
    public AudioClip cluck2;
    public AudioClip chickenAttack;
    public AudioClip chickenCry;

    //Body
    private Rigidbody2D thisRigidBody;

    public GameObject death;

    //Life
    public int Life = 100;

    //powerUp
    public enum PowerUp {
        NO, SUPER_SPEED, SONIC, CHICKEN, INVISIBLE, RAPID_FIRE, SHIELD,
        VAMPIRE, BABY, FIRESHOES, GOLDEN_DRUNK, DOUBLE
    };
    public PowerUp currentPower = PowerUp.NO;

    //Movement and orientation
    public float moveSpeed;
    private Vector2 moveInput;
    private Vector2 moveVelocity;
    private Vector2 orientInput;

    //Ranged Shooting
    public GameObject rangedAttack;
    public Transform crossbow;
    public Transform sword;
    public float reloadTime;
    public float fireTrigger;
    public bool canShoot = true;
    public float shootDelay = .7f;
    private float shootSpeed = 500f;

	public bool blockShoot = false;

    public Sprite normalSprite;

    public Sprite chickenSprite;

    public Sprite babySprite;

    public Sprite vampireSprite;

    public Sprite rapidSprite;

	public Sprite goldenDrunkSprite;

	public Sprite superSpeedSprite;

	public Sprite sonicSprite;

	public Sprite doubleDamageSprite;

    //Shield
    public bool shielded;
    private int shieldTimer = 100;
    public GameObject shield;

    private float vampTimer;
    private float drunkTimer;
    private bool clockwiseDrunk;
    private float rotate = 0;

    private const float vampTime = 0.2f;
    private const float drunkTime = 0.01f;
    private const float rotateStart = 0;
    private Vector3 rotateAngle = new Vector3(0, 0, 0);

    public bool swordSwipe = true;
    private bool leftIn = false;
    private float swipeTimer;
    private float swipeRotate = 75;

    private float fireTimer;
    private const float fireTime = 0.05f;

    private const float swipeTime = 0.001f;

    //Fire

    public GameObject fire;
    private const float fireDelay = 0.5f;
    private Queue<Vector3> positions;

    public bool burning;
    private int burnTicks;
    private float burnTime;
    private const float burnTimeLength = 0.1F;
    
    public int countdown = 3;
    public float second = 1.0f;
    public bool start = false;

    // Use this for initialization
    void Start() {
        thisRigidBody = GetComponent<Rigidbody2D>();
        shield.SetActive(false);
        positions = new Queue<Vector3>();


        source = GetComponent<AudioSource>();
		GetComponent<SpriteRenderer>().sprite = normalSprite;
		GetComponent<SpriteRenderer>().enabled = true;
		sword.GetComponent<SpriteRenderer>().enabled = false;
    }

    // Update is called once per frame
    void Update() {

        second -= Time.deltaTime;
        if (second < 0 && start == false) {
            second = 1;
            countdown--;
            if (countdown == -1) {
                start = true;
            }
        }

        if (start) {

            //Burning
            if (burning) {
                burnTime -= Time.deltaTime;
                if (burnTime < 0) {
                    burnTime = burnTimeLength;
                    burnTicks--;
                    harmThisPlayer(5);
                    if (burnTicks <= 0) {
                        burning = false;
                    }
                }
            }

            //Drunk Bond
            if (currentPower == PowerUp.GOLDEN_DRUNK) {
                drunkTimer -= Time.deltaTime;
                if (drunkTimer < 0) {
                    drunkTimer = drunkTime;
                    if (clockwiseDrunk) {
                        rotate = rotate + 1;
                        if (rotate == 25) {
                            clockwiseDrunk = false;
                        }
                    } else {
                        rotate = rotate - 1;
                        if (rotate == -25) {
                            clockwiseDrunk = true;
                        }
                    }
                }
            } else {
                rotate = 0;
            }

            crossbow.transform.localPosition = new Vector3(0.15f * Mathf.Cos(Mathf.Deg2Rad * rotate), 0.24f * Mathf.Sin(Mathf.Deg2Rad * rotate), 0);
            crossbow.transform.localRotation = Quaternion.Euler(0, 0, rotate);

            if (InputManager.GetAxis("Left Trigger", player) > 0.1 && !leftIn && !shielded) {
                swordSwipe = false;
                swipeTimer = swipeTime;
                leftIn = true;
            }

            if (InputManager.GetAxis("Left Trigger", player) < 0.05) {
                leftIn = false;
            }

            if (swordSwipe == false) {
                swipeTimer -= Time.deltaTime;
                swipeRotate = swipeRotate - 8;
                if (swipeRotate <= -75) {
                    swipeRotate = 75;
                    swordSwipe = true;
                }
            }

            if (burning) {
                GetComponentInChildren<FireParticleEffect>().fireEnabled = true;
            } else {
                GetComponentInChildren<FireParticleEffect>().fireEnabled = false;
            }

            if (shielded) {
                swipeRotate = 75;
                swordSwipe = true;
            }

            if (swordSwipe == true) {
                sword.GetComponent<SpriteRenderer>().enabled = false;
                if (!shielded)
                    crossbow.GetComponent<SpriteRenderer>().enabled = true;
            } else {
                sword.GetComponent<SpriteRenderer>().enabled = true;
                crossbow.GetComponent<SpriteRenderer>().enabled = false;
            }

            sword.transform.localPosition = new Vector3(0.24f * Mathf.Cos(Mathf.Deg2Rad * swipeRotate), 0.24f * Mathf.Sin(Mathf.Deg2Rad * swipeRotate), 0);
            sword.transform.localRotation = Quaternion.Euler(0, 0, swipeRotate);

            if (currentPower == PowerUp.VAMPIRE) {
                vampTimer -= Time.deltaTime;
                if (vampTimer < 0) {
                    vampTimer = vampTime;
                    harmThisPlayer(1);
                }
            }

            if (currentPower == PowerUp.RAPID_FIRE) {
                shootDelay = 0.1f;
            } else if (currentPower == PowerUp.CHICKEN) {
                shootDelay = 0.2f;
            } else {
                shootDelay = 0.7f;
            }

            //Movement:

            float deadzone = 0.3f;

            //Orientation:
            Vector2 newOrientInput = new Vector2(InputManager.GetAxisRaw("Right Stick Horizontal", player), InputManager.GetAxisRaw("Right Stick Vertical", player));
            if (newOrientInput.magnitude > deadzone)
                orientInput = newOrientInput;
            transform.eulerAngles = new Vector3(0, 0, Mathf.Atan2(orientInput.y, orientInput.x) * 180 / Mathf.PI);

            //Movement:
            moveInput = new Vector2(InputManager.GetAxisRaw("Left Stick Horizontal", player), InputManager.GetAxisRaw("Left Stick Vertical", player));
            if (moveInput.magnitude < deadzone)
                moveInput = Vector2.zero;

            if (currentPower == PowerUp.SUPER_SPEED) {
                moveVelocity = moveInput * (moveSpeed * 10);
            } else if (currentPower == PowerUp.SONIC) {
                moveVelocity = orientInput.normalized * (moveSpeed * 2);
            } else {
                moveVelocity = moveInput * moveSpeed;
            }

            if (currentPower == PowerUp.CHICKEN) {
                GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = chickenSprite;
                blockShoot = true;
                crossbow.GetComponent<SpriteRenderer>().enabled = false;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.BABY) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                blockShoot = false;
                GetComponent<SpriteRenderer>().sprite = babySprite;
                CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
                foreach (var bound in bounds) {
                    bound.radius = 0.1f;
                }
            } else if (currentPower == PowerUp.INVISIBLE) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = false;
                crossbow.GetComponent<SpriteRenderer>().enabled = false;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.RAPID_FIRE) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                crossbow.GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = rapidSprite;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.VAMPIRE) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = vampireSprite;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.GOLDEN_DRUNK) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = goldenDrunkSprite;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.SUPER_SPEED) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = superSpeedSprite;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.SONIC) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = sonicSprite;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else if (currentPower == PowerUp.DOUBLE) {
                blockShoot = false;
                GetComponent<SpriteRenderer>().enabled = true;
                GetComponent<SpriteRenderer>().sprite = doubleDamageSprite;
				CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
				foreach (var bound in bounds) {
					bound.radius = 0.15f;
				}
            } else {
                GetComponent<SpriteRenderer>().sprite = normalSprite;
                GetComponent<SpriteRenderer>().enabled = true;
                blockShoot = false;
                CircleCollider2D[] bounds = GetComponents<CircleCollider2D>();
                foreach (var bound in bounds) {
                    bound.radius = 0.15f;
                }
            }

            if (currentPower == PowerUp.FIRESHOES) {
                fireTimer -= Time.deltaTime;
                if (fireTimer <= 0) {
                    positions.Enqueue(transform.position);
                    Invoke("SpawnFire", fireDelay);
                    fireTimer = fireTime;
                }
            } else {
                CancelInvoke("SpawnFire");
                positions.Clear();
            }

            Shooting();
            ShieldTimer();


        }
    }

    void SpawnFire() {
        Instantiate(fire, positions.Dequeue(), Quaternion.identity);
    }

    void Shooting() {
        fireTrigger = InputManager.GetAxis("Right Trigger", player);
        if (fireTrigger > 0 && canShoot && !blockShoot && !shielded && swordSwipe) {
            Quaternion rotationOnCreation = Quaternion.Euler(0, 0, -90);
            GameObject boltInstance = Instantiate(rangedAttack, crossbow.position, crossbow.rotation * rotationOnCreation) as GameObject;
            boltInstance.GetComponent<BoltController>().player = player;
            //Set Damage
            if (currentPower == PowerUp.DOUBLE) {
                boltInstance.GetComponent<BoltController>().damage = 40;
            } else if (currentPower == PowerUp.GOLDEN_DRUNK) {
                boltInstance.GetComponent<BoltController>().damage = 1000;
            } else {
                boltInstance.GetComponent<BoltController>().damage = 20;
            }
            //Vampire Arrows
            if (currentPower == PowerUp.VAMPIRE) {
                boltInstance.GetComponent<BoltController>().bloodthirsty = true;
            }
            boltInstance.GetComponent<Rigidbody2D>().AddForce(crossbow.right * shootSpeed);
            if (currentPower == PowerUp.RAPID_FIRE) {
                boltInstance.GetComponent<BoltController>().damage = 10;
                gameObject.GetComponent<Rigidbody2D>().AddForce(-(crossbow.right * (shootSpeed/2)));
            }
            canShoot = false;
            Invoke("ShootDelay", shootDelay);

        } else if (fireTrigger > 0 && currentPower == PowerUp.CHICKEN && canShoot) {
            gameObject.GetComponent<Rigidbody2D>().AddForce((crossbow.right * (shootSpeed*2)));
            canShoot = false;
            Invoke("ShootDelay", shootDelay);
        }

    }
    void ShootDelay() {
        canShoot = true;
    }

    public void harmThisPlayer(int damage) {
        if (currentPower == PowerUp.DOUBLE) {
            Life = Life - damage * 2;
        } else if (currentPower == PowerUp.BABY) {
            Life = -100;
        } else {
            Life = Life - damage;
        }
        if (Life <= 0) {
            Debug.Log(this.transform);
            Instantiate(death, this.transform.position, this.transform.rotation);
            Destroy(this.gameObject);
        }
    }

    float AngleBetweenTwoPoints(Vector3 a, Vector3 b) {
        return Mathf.Atan2(a.y - b.y, a.x - b.x) * Mathf.Rad2Deg;
    }

    void FixedUpdate()
    {
        if (start) {
            thisRigidBody.AddForce(moveVelocity);
        }
    }

    void OnTriggerEnter2D(Collider2D other) {
        //Debug.Log(other.gameObject.tag);
        if (other.gameObject.CompareTag("superSpeed")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.SUPER_SPEED;
        } else if (other.gameObject.CompareTag("sonic")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.SONIC;
        } else if (other.gameObject.CompareTag("chicken")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.CHICKEN;
        } else if (other.gameObject.CompareTag("invisible")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.INVISIBLE;
        } else if (other.gameObject.CompareTag("rapidFire")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.RAPID_FIRE;
        } else if (other.gameObject.CompareTag("shield")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.SHIELD;
            shield.SetActive(true);
        } else if (other.gameObject.CompareTag("vampire")) {
            Destroy(other.gameObject);
            vampTimer = vampTime;
            currentPower = PowerUp.VAMPIRE;
        } else if (other.gameObject.CompareTag("baby")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.BABY;
        } else if (other.gameObject.CompareTag("fireshoes")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.FIRESHOES;
        } else if (other.gameObject.CompareTag("goldenDrunk")) {
            fireTimer = fireTime;
            Destroy(other.gameObject);
            currentPower = PowerUp.GOLDEN_DRUNK;
        } else if (other.gameObject.CompareTag("doubleDamage")) {
            Destroy(other.gameObject);
            currentPower = PowerUp.DOUBLE;
        } else if (other.gameObject.CompareTag("Arrow")) {
            GameObject arrow = other.gameObject;
            if (!shielded && arrow.GetComponent<BoltController>().player != player) {
                harmThisPlayer(arrow.GetComponent<BoltController>().damage);
            }
        } else if (other.gameObject.CompareTag("Sword")) {
            GameObject sword = other.gameObject;
            if (sword.GetComponentInParent<CharacterControler>().swordSwipe == false) {
                CharacterControler otherPlayer = sword.GetComponentInParent<CharacterControler>();
                otherPlayer.swipeRotate = 75;
                otherPlayer.swordSwipe = true;
                harmThisPlayer(40);
                gameObject.GetComponent<Rigidbody2D>().AddForce((otherPlayer.crossbow.right * (shootSpeed / 2)));
                otherPlayer.gameObject.GetComponent<Rigidbody2D>().AddForce(-(otherPlayer.crossbow.right * (shootSpeed / 2)));
            }
        } else if (other.gameObject.CompareTag("Fire")) {
            Debug.Log("FIRE!!!!!!");
            if (!burning) {
                burnTime = burnTimeLength;
                burnTicks = 5;
            }
            burning = true;
        }
    }

    void ShieldTimer() {
        if (currentPower == PowerUp.SHIELD) {
            if (shielded) {

                shieldTimer = shieldTimer - 1;
                if (shieldTimer <= 0) {
                    shielded = false;
                    shield.SetActive(false);
                    crossbow.GetComponent<SpriteRenderer>().enabled = true;
                }
            } else {
                shieldTimer = shieldTimer + 1;
                if (shieldTimer >= 100) {
                    shielded = true;
                    shield.SetActive(true);
                    crossbow.GetComponent<SpriteRenderer>().enabled = false;
                }
            }
        } else {
            shieldTimer = 100;
            shielded = false;
            shield.SetActive(false);
        }
    }

    public PlayerID getPlayer() {
        return player;
    }

    public int getHealth() {
        return Life;
    }

    public void heal() {
        Life += 20;
        if (Life > 100) {
            Life = 100;
        }
    }
}
