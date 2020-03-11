package com.vikas.concurrency.chp4;

/**
 * Since, SynchronizedRGB is not an immutable class, if we try to get the color
 * using one thread and another thread sets thread name in between, we get
 * inconsistent results. Hence, mutable objects needs to be handled carefully.
 * 
 * The following rules define a simple strategy for creating immutable objects.
 * Not all classes documented as "immutable" follow these rules. This does not
 * necessarily mean the creators of these classes were sloppy — they may have
 * good reason for believing that instances of their classes never change after
 * construction. However, such strategies require sophisticated analysis and are
 * not for beginners.
 * 
 * Don't provide "setter" methods — methods that modify fields or objects
 * referred to by fields. Make all fields final and private. Don't allow
 * subclasses to override methods. The simplest way to do this is to declare the
 * class as final. A more sophisticated approach is to make the constructor
 * private and construct instances in factory methods. If the instance fields
 * include references to mutable objects, don't allow those objects to be
 * changed: Don't provide methods that modify the mutable objects. Don't share
 * references to the mutable objects. Never store references to external,
 * mutable objects passed to the constructor; if necessary, create copies, and
 * store references to the copies. Similarly, create copies of your internal
 * mutable objects when necessary to avoid returning the originals in your
 * methods.
 * 
 */
public class SynchronizedRGB {

	// Values must be between 0 and 255.
	private int red;
	private int green;
	private int blue;
	private String name;

	private void check(int red, int green, int blue) {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	public SynchronizedRGB(int red, int green, int blue, String name) {
		check(red, green, blue);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.name = name;
	}

	public void set(int red, int green, int blue, String name) {
		check(red, green, blue);
		synchronized (this) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.name = name;
		}
	}

	public synchronized int getRGB() {
		return ((red << 16) | (green << 8) | blue);
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void invert() {
		red = 255 - red;
		green = 255 - green;
		blue = 255 - blue;
		name = "Inverse of " + name;
	}

	public static void main(String[] args) {
		SynchronizedRGB color = new SynchronizedRGB(0, 0, 0, "Pitch Black");
		Thread thread1 = new Thread(() -> {

			synchronized (color) {
				int myColorInt = color.getRGB();
				String myColorName = color.getName();
				System.out.println("Color is " + myColorName);
			}

			/*
			 * int myColorInt = color.getRGB(); //Statement 1 try { Thread.sleep(2000); }
			 * catch (InterruptedException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } String myColorName = color.getName(); //Statement 2
			 * System.out.println("Color is "+myColorName);
			 */
		});

		// If another thread invokes color.set after Statement 1 but before Statement 2,
		// the value of myColorInt won't match the value of myColorName. To avoid this
		// outcome, the two statements must be bound together in a synchronized context
		Thread thread2 = new Thread(() -> {
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			color.set(12, 12, 12, "Test");

		});

		thread1.start();
		thread2.start();
	}
}

/** Immutable version of previous class implementation*/
final class ImmutableRGB {

    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
    final private String name;

    private void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
            || green < 0 || green > 255
            || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutableRGB(int red,
                        int green,
                        int blue,
                        String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }


    public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public String getName() {
        return name;
    }

    public ImmutableRGB invert() {
        return new ImmutableRGB(255 - red,
                       255 - green,
                       255 - blue,
                       "Inverse of " + name);
    }
}

