package rat.calculation.planet.atmosphere.models;

import java.util.Iterator;
import java.util.LinkedList;

import rat.calculation.planet.atmosphere.AtmosphereData;
import rat.calculation.planet.atmosphere.AtmosphereModel;

/**
 * Implementation of the US Standard Atmosphere model, up to 900 km.
 * 
 * 
 * @author Gerhard Mesch, Michael Sams
 **/
@SuppressWarnings("serial")
public class USStandardAtmosphere extends AtmosphereModel {

	// TODO: implement a search tree for this lookup
	// or store the last access and always start from there
	private LinkedList<AtmosphereDataEntry> atmosphereData;

	public USStandardAtmosphere() {
		super("US standard atmosphere");
		atmosphereData = new LinkedList<AtmosphereDataEntry>();
		// CiraEntry( height [m], temperature [K], density [kg m^-3] )
		atmosphereData.add(new AtmosphereDataEntry(0, 288.15, 1.225226));
		atmosphereData.add(new AtmosphereDataEntry(1000, 281.65, 1.111853));
		atmosphereData.add(new AtmosphereDataEntry(2000, 275.15, 1.006724));
		atmosphereData.add(new AtmosphereDataEntry(3000, 268.65, 0.9094092));
		atmosphereData.add(new AtmosphereDataEntry(4000, 262.15, 0.8194915));
		atmosphereData.add(new AtmosphereDataEntry(5000, 255.65, 0.7365669));
		atmosphereData.add(new AtmosphereDataEntry(6000, 249.15, 0.660245));
		atmosphereData.add(new AtmosphereDataEntry(7000, 242.65, 0.5901481));
		atmosphereData.add(new AtmosphereDataEntry(8000, 236.15, 0.5259115));
		atmosphereData.add(new AtmosphereDataEntry(9000, 229.65, 0.4671834));
		atmosphereData.add(new AtmosphereDataEntry(10000, 223.15, 0.4136243));
		atmosphereData.add(new AtmosphereDataEntry(11000, 216.65, 0.3649073));
		atmosphereData.add(new AtmosphereDataEntry(12000, 216.65, 0.3127722));
		atmosphereData.add(new AtmosphereDataEntry(13000, 216.65, 0.2673039));
		atmosphereData.add(new AtmosphereDataEntry(14000, 216.65, 0.2284567));
		atmosphereData.add(new AtmosphereDataEntry(15000, 216.65, 0.1952648));
		atmosphereData.add(new AtmosphereDataEntry(16000, 216.65, 0.1669035));
		atmosphereData.add(new AtmosphereDataEntry(17000, 216.65, 0.1426687));
		atmosphereData.add(new AtmosphereDataEntry(18000, 216.65, 0.1219588));
		atmosphereData.add(new AtmosphereDataEntry(19000, 216.65, 0.1042603));
		atmosphereData.add(new AtmosphereDataEntry(20000, 216.65, 0.08913465));
		atmosphereData.add(new AtmosphereDataEntry(21000, 217.59, 0.07590299));
		atmosphereData.add(new AtmosphereDataEntry(22000, 218.59, 0.06466749));
		atmosphereData.add(new AtmosphereDataEntry(23000, 219.59, 0.05513812));
		atmosphereData.add(new AtmosphereDataEntry(24000, 220.59, 0.04704935));
		atmosphereData.add(new AtmosphereDataEntry(25000, 221.59, 0.04017797));
		atmosphereData.add(new AtmosphereDataEntry(26000, 222.59, 0.03433618));
		atmosphereData.add(new AtmosphereDataEntry(27000, 223.59, 0.02936586));
		atmosphereData.add(new AtmosphereDataEntry(28000, 224.59, 0.02513375));
		atmosphereData.add(new AtmosphereDataEntry(29000, 225.59, 0.02152746));
		atmosphereData.add(new AtmosphereDataEntry(30000, 226.59, 0.01845212));
		atmosphereData.add(new AtmosphereDataEntry(31000, 227.59, 0.01582761));
		atmosphereData.add(new AtmosphereDataEntry(32000, 228.59, 0.01358616));
		atmosphereData.add(new AtmosphereDataEntry(33000, 231, 0.01178657));
		atmosphereData.add(new AtmosphereDataEntry(34000, 233.8, 0.01006872));
		atmosphereData.add(new AtmosphereDataEntry(35000, 236.6, 0.008617783));
		atmosphereData.add(new AtmosphereDataEntry(36000, 239.4, 0.007389775));
		atmosphereData.add(new AtmosphereDataEntry(37000, 242.2, 0.006348376));
		atmosphereData.add(new AtmosphereDataEntry(38000, 245, 0.005463506));
		atmosphereData.add(new AtmosphereDataEntry(39000, 247.8, 0.004710206));
		atmosphereData.add(new AtmosphereDataEntry(40000, 250.6, 0.00406772));
		atmosphereData.add(new AtmosphereDataEntry(41000, 253.4, 0.00351875));
		atmosphereData.add(new AtmosphereDataEntry(42000, 256.2, 0.003048848));
		atmosphereData.add(new AtmosphereDataEntry(43000, 259, 0.002645928));
		atmosphereData.add(new AtmosphereDataEntry(44000, 261.8, 0.002299853));
		atmosphereData.add(new AtmosphereDataEntry(45000, 264.6, 0.002002108));
		atmosphereData.add(new AtmosphereDataEntry(46000, 267.4, 0.001745525));
		atmosphereData.add(new AtmosphereDataEntry(47000, 270.2, 0.001524062));
		atmosphereData.add(new AtmosphereDataEntry(48000, 270.65, 0.001376372));
		atmosphereData.add(new AtmosphereDataEntry(49000, 270.65, 0.001215469));
		atmosphereData.add(new AtmosphereDataEntry(50000, 270.65, 0.001073418));
		atmosphereData
				.add(new AtmosphereDataEntry(51000, 270.65, 0.0009480059));
		atmosphereData.add(new AtmosphereDataEntry(52000, 269, 0.000842212));
		atmosphereData.add(new AtmosphereDataEntry(53000, 266.2, 0.0007506338));
		atmosphereData.add(new AtmosphereDataEntry(54000, 263.4, 0.0006682266));
		atmosphereData.add(new AtmosphereDataEntry(55000, 260.6, 0.0005941518));
		atmosphereData.add(new AtmosphereDataEntry(56000, 257.8, 0.0005276401));
		atmosphereData.add(new AtmosphereDataEntry(57000, 255, 0.0004679865));
		atmosphereData.add(new AtmosphereDataEntry(58000, 252.2, 0.0004145452));
		atmosphereData.add(new AtmosphereDataEntry(59000, 249.4, 0.0003667254));
		atmosphereData.add(new AtmosphereDataEntry(60000, 246.6, 0.0003239873));
		atmosphereData.add(new AtmosphereDataEntry(61000, 243.8, 0.0002858376));
		atmosphereData.add(new AtmosphereDataEntry(62000, 241, 0.0002518265));
		atmosphereData.add(new AtmosphereDataEntry(63000, 238.2, 0.000221544));
		atmosphereData
				.add(new AtmosphereDataEntry(64000, 235.39, 0.0001946168));
		atmosphereData
				.add(new AtmosphereDataEntry(65000, 232.59, 0.0001707054));
		atmosphereData
				.add(new AtmosphereDataEntry(66000, 229.79, 0.0001495012));
		atmosphereData
				.add(new AtmosphereDataEntry(67000, 226.99, 0.0001307243));
		atmosphereData
				.add(new AtmosphereDataEntry(68000, 224.19, 0.0001141208));
		atmosphereData.add(new AtmosphereDataEntry(69000, 221.39, 9.94611e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(70000, 218.59, 8.653718e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(71000, 215.79, 7.516131e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(72000, 214.24, 6.875176e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(73000, 212.24, 5.934118e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(74000, 210.24, 5.114997e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(75000, 208.24, 4.402915e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(76000, 206.24, 3.784681e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(77000, 204.24, 3.248633e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(78000, 202.24, 2.784469e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(79000, 200.24, 2.383097e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(80000, 198.24, 2.036506e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(81000, 196.24, 1.737646e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(82000, 194.24, 1.480317e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(83000, 192.24, 1.259076e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(84000, 190.24, 1.069149e-05));
		atmosphereData
				.add(new AtmosphereDataEntry(85000, 188.24, 9.063558e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(86000, 186.93, 8.112899e-06));
		atmosphereData.add(new AtmosphereDataEntry(87000, 188.59, 6.73577e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(88000, 190.24, 5.602022e-06));
		atmosphereData.add(new AtmosphereDataEntry(89000, 191.9, 4.666976e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(90000, 193.55, 3.894455e-06));
		atmosphereData.add(new AtmosphereDataEntry(91000, 195.2, 3.255109e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(92000, 196.85, 2.725084e-06));
		atmosphereData.add(new AtmosphereDataEntry(93000, 198.5, 2.284956e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(94000, 200.15, 1.918881e-06));
		atmosphereData.add(new AtmosphereDataEntry(95000, 201.8, 1.613908e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(96000, 203.45, 1.359438e-06));
		atmosphereData.add(new AtmosphereDataEntry(97000, 205.1, 1.146777e-06));
		atmosphereData
				.add(new AtmosphereDataEntry(98000, 206.74, 9.687842e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(99000, 208.39, 8.195839e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(100000, 210.04, 6.941928e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(101000, 214.79, 5.805864e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(102000, 219.52, 4.875891e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(103000, 224.25, 4.111126e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(104000, 228.96, 3.479461e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(105000, 233.66, 2.95554e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(106000, 238.36, 2.51924e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(107000, 243.04, 2.154506e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(108000, 247.71, 1.848474e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(109000, 252.37, 1.590786e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(110000, 257.01, 1.373065e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(111000, 266.42, 1.167917e-07));
		atmosphereData
				.add(new AtmosphereDataEntry(112000, 275.79, 9.993087e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(113000, 285.12, 8.597552e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(114000, 294.42, 7.434909e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(115000, 303.69, 6.460356e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(116000, 312.92, 5.638785e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(117000, 322.12, 4.942459e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(118000, 331.29, 4.349308e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(119000, 340.42, 3.841646e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(120000, 349.52, 3.405206e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(121000, 368.4, 2.952383e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(122000, 387.22, 2.578624e-08));
		atmosphereData.add(new AtmosphereDataEntry(123000, 406, 2.267142e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(124000, 424.72, 2.005293e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(125000, 443.38, 1.78342e-08));
		atmosphereData.add(new AtmosphereDataEntry(126000, 462, 1.594057e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(127000, 480.56, 1.431368e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(128000, 499.07, 1.290742e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(129000, 517.52, 1.168502e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(130000, 535.92, 1.06169e-08));
		atmosphereData
				.add(new AtmosphereDataEntry(131000, 554.27, 9.67907e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(132000, 572.57, 8.851923e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(133000, 590.81, 8.119328e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(134000, 609.01, 7.467925e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(135000, 627.14, 6.88658e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(136000, 645.23, 6.36596e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(137000, 663.26, 5.898203e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(138000, 681.24, 5.476651e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(139000, 699.16, 5.095635e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(140000, 717.04, 4.750314e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(141000, 734.86, 4.43653e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(142000, 752.63, 4.150698e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(143000, 770.34, 3.889719e-09));
		atmosphereData.add(new AtmosphereDataEntry(144000, 788, 3.650902e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(145000, 805.61, 3.431899e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(146000, 823.16, 3.230661e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(147000, 840.67, 3.04539e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(148000, 858.12, 2.874504e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(149000, 875.51, 2.716607e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(150000, 892.86, 2.570466e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(151000, 905.92, 2.447255e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(152000, 918.96, 2.331722e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(153000, 931.97, 2.223283e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(154000, 944.96, 2.121404e-09));
		atmosphereData.add(new AtmosphereDataEntry(155000, 957.92, 2.0256e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(156000, 970.85, 1.935429e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(157000, 983.75, 1.850484e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(158000, 996.63, 1.770396e-09));
		atmosphereData.add(new AtmosphereDataEntry(159000, 1009.48,
				1.694824e-09));
		atmosphereData.add(new AtmosphereDataEntry(160000, 1022.26,
				1.623692e-09));
		atmosphereData.add(new AtmosphereDataEntry(161000, 1030.84,
				1.563072e-09));
		atmosphereData.add(new AtmosphereDataEntry(162000, 1039.42,
				1.505238e-09));
		atmosphereData.add(new AtmosphereDataEntry(163000, 1047.98,
				1.450039e-09));
		atmosphereData.add(new AtmosphereDataEntry(164000, 1056.53,
				1.397333e-09));
		atmosphereData.add(new AtmosphereDataEntry(165000, 1065.07,
				1.346987e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(166000, 1073.6, 1.298876e-09));
		atmosphereData.add(new AtmosphereDataEntry(167000, 1082.12,
				1.252882e-09));
		atmosphereData.add(new AtmosphereDataEntry(168000, 1090.63,
				1.208895e-09));
		atmosphereData.add(new AtmosphereDataEntry(169000, 1099.13,
				1.166811e-09));
		atmosphereData.add(new AtmosphereDataEntry(170000, 1107.66,
				1.126331e-09));
		atmosphereData
				.add(new AtmosphereDataEntry(171000, 1112.7, 1.090421e-09));
		atmosphereData.add(new AtmosphereDataEntry(172000, 1117.72,
				1.055861e-09));
		atmosphereData.add(new AtmosphereDataEntry(173000, 1122.73,
				1.022593e-09));
		atmosphereData.add(new AtmosphereDataEntry(174000, 1127.72,
				9.905614e-10));
		atmosphereData.add(new AtmosphereDataEntry(175000, 1132.69,
				9.597136e-10));
		atmosphereData.add(new AtmosphereDataEntry(176000, 1137.65,
				9.299993e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(177000, 1142.6, 9.013707e-10));
		atmosphereData.add(new AtmosphereDataEntry(178000, 1147.52,
				8.737822e-10));
		atmosphereData.add(new AtmosphereDataEntry(179000, 1152.44,
				8.471903e-10));
		atmosphereData.add(new AtmosphereDataEntry(180000, 1157.33,
				8.215538e-10));
		atmosphereData.add(new AtmosphereDataEntry(181000, 1162.21,
				7.968331e-10));
		atmosphereData.add(new AtmosphereDataEntry(182000, 1167.08,
				7.729908e-10));
		atmosphereData.add(new AtmosphereDataEntry(183000, 1171.93,
				7.499908e-10));
		atmosphereData.add(new AtmosphereDataEntry(184000, 1176.76,
				7.277991e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(185000, 1181.58, 7.06383e-10));
		atmosphereData.add(new AtmosphereDataEntry(186000, 1186.38,
				6.857113e-10));
		atmosphereData.add(new AtmosphereDataEntry(187000, 1191.17,
				6.657543e-10));
		atmosphereData.add(new AtmosphereDataEntry(188000, 1195.94,
				6.464836e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(189000, 1200.7, 6.27872e-10));
		atmosphereData.add(new AtmosphereDataEntry(190000, 1205.44,
				6.098936e-10));
		atmosphereData.add(new AtmosphereDataEntry(191000, 1208.54,
				5.933875e-10));
		atmosphereData.add(new AtmosphereDataEntry(192000, 1211.64,
				5.773909e-10));
		atmosphereData.add(new AtmosphereDataEntry(193000, 1214.72,
				5.618863e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(194000, 1217.8, 5.468568e-10));
		atmosphereData.add(new AtmosphereDataEntry(195000, 1220.86,
				5.322859e-10));
		atmosphereData.add(new AtmosphereDataEntry(196000, 1223.92,
				5.181581e-10));
		atmosphereData.add(new AtmosphereDataEntry(197000, 1226.96,
				5.044583e-10));
		atmosphereData.add(new AtmosphereDataEntry(198000, 1230, 4.911718e-10));
		atmosphereData.add(new AtmosphereDataEntry(199000, 1233.02,
				4.782848e-10));
		atmosphereData.add(new AtmosphereDataEntry(200000, 1236.04,
				4.657837e-10));
		atmosphereData.add(new AtmosphereDataEntry(201000, 1239.04,
				4.536557e-10));
		atmosphereData.add(new AtmosphereDataEntry(202000, 1242.04,
				4.418882e-10));
		atmosphereData.add(new AtmosphereDataEntry(203000, 1245.02,
				4.304692e-10));
		atmosphereData.add(new AtmosphereDataEntry(204000, 1248, 4.193872e-10));
		atmosphereData.add(new AtmosphereDataEntry(205000, 1250.96,
				4.086311e-10));
		atmosphereData.add(new AtmosphereDataEntry(206000, 1253.92,
				3.981899e-10));
		atmosphereData.add(new AtmosphereDataEntry(207000, 1256.86,
				3.880536e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(208000, 1259.8, 3.782119e-10));
		atmosphereData.add(new AtmosphereDataEntry(209000, 1262.72,
				3.686554e-10));
		atmosphereData.add(new AtmosphereDataEntry(210000, 1265.64,
				3.593748e-10));
		atmosphereData.add(new AtmosphereDataEntry(211000, 1268.54,
				3.503612e-10));
		atmosphereData.add(new AtmosphereDataEntry(212000, 1271.44,
				3.416059e-10));
		atmosphereData.add(new AtmosphereDataEntry(213000, 1274.32,
				3.331006e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(214000, 1277.2, 3.248373e-10));
		atmosphereData.add(new AtmosphereDataEntry(215000, 1280.06,
				3.168084e-10));
		atmosphereData.add(new AtmosphereDataEntry(216000, 1282.92,
				3.090063e-10));
		atmosphereData.add(new AtmosphereDataEntry(217000, 1285.76,
				3.014238e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(218000, 1288.6, 2.940541e-10));
		atmosphereData.add(new AtmosphereDataEntry(219000, 1291.42,
				2.868904e-10));
		atmosphereData.add(new AtmosphereDataEntry(220000, 1294.24,
				2.799263e-10));
		atmosphereData.add(new AtmosphereDataEntry(221000, 1297.04,
				2.731555e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(222000, 1299.83, 2.66572e-10));
		atmosphereData.add(new AtmosphereDataEntry(223000, 1302.62,
				2.601699e-10));
		atmosphereData.add(new AtmosphereDataEntry(224000, 1305.39,
				2.539438e-10));
		atmosphereData.add(new AtmosphereDataEntry(225000, 1308.16,
				2.478881e-10));
		atmosphereData.add(new AtmosphereDataEntry(226000, 1310.91,
				2.419976e-10));
		atmosphereData.add(new AtmosphereDataEntry(227000, 1313.66,
				2.362673e-10));
		atmosphereData.add(new AtmosphereDataEntry(228000, 1316.39,
				2.306922e-10));
		atmosphereData.add(new AtmosphereDataEntry(229000, 1319.12,
				2.252677e-10));
		atmosphereData.add(new AtmosphereDataEntry(230000, 1321.83,
				2.199892e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(231000, 1323.69, 2.14989e-10));
		atmosphereData.add(new AtmosphereDataEntry(232000, 1325.53,
				2.101163e-10));
		atmosphereData.add(new AtmosphereDataEntry(233000, 1327.37,
				2.053676e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(234000, 1329.2, 2.007393e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(235000, 1331.02, 1.96228e-10));
		atmosphereData.add(new AtmosphereDataEntry(236000, 1332.83,
				1.918305e-10));
		atmosphereData.add(new AtmosphereDataEntry(237000, 1334.64,
				1.875436e-10));
		atmosphereData.add(new AtmosphereDataEntry(238000, 1336.43,
				1.833643e-10));
		atmosphereData.add(new AtmosphereDataEntry(239000, 1338.22,
				1.792895e-10));
		atmosphereData.add(new AtmosphereDataEntry(240000, 1340, 1.753163e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(241000, 1341.78, 1.71442e-10));
		atmosphereData.add(new AtmosphereDataEntry(242000, 1343.54,
				1.676638e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(243000, 1345.3, 1.639791e-10));
		atmosphereData.add(new AtmosphereDataEntry(244000, 1347.05,
				1.603854e-10));
		atmosphereData.add(new AtmosphereDataEntry(245000, 1348.79,
				1.568801e-10));
		atmosphereData.add(new AtmosphereDataEntry(246000, 1350.52,
				1.534608e-10));
		atmosphereData.add(new AtmosphereDataEntry(247000, 1352.25,
				1.501253e-10));
		atmosphereData.add(new AtmosphereDataEntry(248000, 1353.96,
				1.468712e-10));
		atmosphereData.add(new AtmosphereDataEntry(249000, 1355.67,
				1.436963e-10));
		atmosphereData.add(new AtmosphereDataEntry(250000, 1357.37,
				1.405985e-10));
		atmosphereData.add(new AtmosphereDataEntry(251000, 1359.07,
				1.375757e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(252000, 1360.75, 1.34626e-10));
		atmosphereData.add(new AtmosphereDataEntry(253000, 1362.43,
				1.317473e-10));
		atmosphereData
				.add(new AtmosphereDataEntry(254000, 1364.1, 1.289378e-10));
		atmosphereData.add(new AtmosphereDataEntry(255000, 1365.76,
				1.261957e-10));
		atmosphereData.add(new AtmosphereDataEntry(256000, 1367.41,
				1.235191e-10));
		atmosphereData.add(new AtmosphereDataEntry(257000, 1369.06,
				1.209063e-10));
		atmosphereData.add(new AtmosphereDataEntry(258000, 1370.69,
				1.183556e-10));
		atmosphereData.add(new AtmosphereDataEntry(259000, 1372.32,
				1.158654e-10));
		atmosphereData.add(new AtmosphereDataEntry(260000, 1373.94,
				1.134341e-10));
		atmosphereData.add(new AtmosphereDataEntry(261000, 1375.56,
				1.110602e-10));
		atmosphereData.add(new AtmosphereDataEntry(262000, 1377.16,
				1.087421e-10));
		atmosphereData.add(new AtmosphereDataEntry(263000, 1378.76,
				1.064785e-10));
		atmosphereData.add(new AtmosphereDataEntry(264000, 1380.35,
				1.042678e-10));
		atmosphereData.add(new AtmosphereDataEntry(265000, 1381.93,
				1.021088e-10));
		atmosphereData.add(new AtmosphereDataEntry(266000, 1383.5, 1e-10));
		atmosphereData.add(new AtmosphereDataEntry(267000, 1385.06,
				9.794024e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(268000, 1386.62, 9.59282e-11));
		atmosphereData.add(new AtmosphereDataEntry(269000, 1388.17,
				9.396266e-11));
		atmosphereData.add(new AtmosphereDataEntry(270000, 1389.71,
				9.204243e-11));
		atmosphereData.add(new AtmosphereDataEntry(271000, 1391.24,
				9.016635e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(272000, 1392.77, 8.83333e-11));
		atmosphereData.add(new AtmosphereDataEntry(273000, 1394.29,
				8.654219e-11));
		atmosphereData.add(new AtmosphereDataEntry(274000, 1395.79,
				8.479195e-11));
		atmosphereData.add(new AtmosphereDataEntry(275000, 1397.29,
				8.308156e-11));
		atmosphereData.add(new AtmosphereDataEntry(276000, 1398.79,
				8.140999e-11));
		atmosphereData.add(new AtmosphereDataEntry(277000, 1400.27,
				7.977629e-11));
		atmosphereData.add(new AtmosphereDataEntry(278000, 1401.75,
				7.817949e-11));
		atmosphereData.add(new AtmosphereDataEntry(279000, 1403.22,
				7.661867e-11));
		atmosphereData.add(new AtmosphereDataEntry(280000, 1404.68,
				7.509294e-11));
		atmosphereData.add(new AtmosphereDataEntry(281000, 1406.13,
				7.360143e-11));
		atmosphereData.add(new AtmosphereDataEntry(282000, 1407.58,
				7.214327e-11));
		atmosphereData.add(new AtmosphereDataEntry(283000, 1409.01,
				7.071764e-11));
		atmosphereData.add(new AtmosphereDataEntry(284000, 1410.44,
				6.932375e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(285000, 1411.86, 6.79608e-11));
		atmosphereData.add(new AtmosphereDataEntry(286000, 1413.27,
				6.662803e-11));
		atmosphereData.add(new AtmosphereDataEntry(287000, 1414.68,
				6.532471e-11));
		atmosphereData.add(new AtmosphereDataEntry(288000, 1416.08,
				6.405011e-11));
		atmosphereData.add(new AtmosphereDataEntry(289000, 1417.46,
				6.280353e-11));
		atmosphereData.add(new AtmosphereDataEntry(290000, 1418.84,
				6.158429e-11));
		atmosphereData.add(new AtmosphereDataEntry(291000, 1420.22,
				6.039172e-11));
		atmosphereData.add(new AtmosphereDataEntry(292000, 1421.58,
				5.922518e-11));
		atmosphereData.add(new AtmosphereDataEntry(293000, 1422.94,
				5.808403e-11));
		atmosphereData.add(new AtmosphereDataEntry(294000, 1424.29,
				5.696766e-11));
		atmosphereData.add(new AtmosphereDataEntry(295000, 1425.63,
				5.587548e-11));
		atmosphereData.add(new AtmosphereDataEntry(296000, 1426.96,
				5.480691e-11));
		atmosphereData.add(new AtmosphereDataEntry(297000, 1428.28,
				5.376137e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(298000, 1429.6, 5.273832e-11));
		atmosphereData.add(new AtmosphereDataEntry(299000, 1430.91,
				5.173722e-11));
		atmosphereData.add(new AtmosphereDataEntry(300000, 1432.21,
				5.075755e-11));
		atmosphereData.add(new AtmosphereDataEntry(301000, 1433.07,
				4.981764e-11));
		atmosphereData.add(new AtmosphereDataEntry(302000, 1433.92,
				4.889707e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(303000, 1434.77, 4.79954e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(304000, 1435.61, 4.71122e-11));
		atmosphereData.add(new AtmosphereDataEntry(305000, 1436.45,
				4.624705e-11));
		atmosphereData.add(new AtmosphereDataEntry(306000, 1437.27,
				4.539956e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(307000, 1438.1, 4.456932e-11));
		atmosphereData.add(new AtmosphereDataEntry(308000, 1438.91,
				4.375596e-11));
		atmosphereData.add(new AtmosphereDataEntry(309000, 1439.72,
				4.295909e-11));
		atmosphereData.add(new AtmosphereDataEntry(310000, 1440.53,
				4.217835e-11));
		atmosphereData.add(new AtmosphereDataEntry(311000, 1441.32,
				4.141338e-11));
		atmosphereData.add(new AtmosphereDataEntry(312000, 1442.11,
				4.066383e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(313000, 1442.9, 3.992937e-11));
		atmosphereData.add(new AtmosphereDataEntry(314000, 1443.68,
				3.920965e-11));
		atmosphereData.add(new AtmosphereDataEntry(315000, 1444.45,
				3.850435e-11));
		atmosphereData.add(new AtmosphereDataEntry(316000, 1445.22,
				3.781316e-11));
		atmosphereData.add(new AtmosphereDataEntry(317000, 1445.98,
				3.713577e-11));
		atmosphereData.add(new AtmosphereDataEntry(318000, 1446.73,
				3.647186e-11));
		atmosphereData.add(new AtmosphereDataEntry(319000, 1447.48,
				3.582116e-11));
		atmosphereData.add(new AtmosphereDataEntry(320000, 1448.22,
				3.518337e-11));
		atmosphereData.add(new AtmosphereDataEntry(321000, 1448.96,
				3.455821e-11));
		atmosphereData.add(new AtmosphereDataEntry(322000, 1449.69,
				3.394541e-11));
		atmosphereData.add(new AtmosphereDataEntry(323000, 1450.41,
				3.334469e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(324000, 1451.13, 3.27558e-11));
		atmosphereData.add(new AtmosphereDataEntry(325000, 1451.84,
				3.217848e-11));
		atmosphereData.add(new AtmosphereDataEntry(326000, 1452.54,
				3.161248e-11));
		atmosphereData.add(new AtmosphereDataEntry(327000, 1453.24,
				3.105755e-11));
		atmosphereData.add(new AtmosphereDataEntry(328000, 1453.93,
				3.051347e-11));
		atmosphereData.add(new AtmosphereDataEntry(329000, 1454.62, 2.998e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(330000, 1455.3, 2.94569e-11));
		atmosphereData.add(new AtmosphereDataEntry(331000, 1455.97,
				2.894396e-11));
		atmosphereData.add(new AtmosphereDataEntry(332000, 1456.64,
				2.844096e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(333000, 1457.3, 2.79477e-11));
		atmosphereData.add(new AtmosphereDataEntry(334000, 1457.96,
				2.746395e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(335000, 1458.6, 2.698953e-11));
		atmosphereData.add(new AtmosphereDataEntry(336000, 1459.25,
				2.652423e-11));
		atmosphereData.add(new AtmosphereDataEntry(337000, 1459.88,
				2.606787e-11));
		atmosphereData.add(new AtmosphereDataEntry(338000, 1460.51,
				2.562025e-11));
		atmosphereData.add(new AtmosphereDataEntry(339000, 1461.14,
				2.518118e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(340000, 1461.76, 2.47505e-11));
		atmosphereData.add(new AtmosphereDataEntry(341000, 1462.37,
				2.432802e-11));
		atmosphereData.add(new AtmosphereDataEntry(342000, 1462.97,
				2.391357e-11));
		atmosphereData.add(new AtmosphereDataEntry(343000, 1463.57,
				2.350699e-11));
		atmosphereData.add(new AtmosphereDataEntry(344000, 1464.16,
				2.310811e-11));
		atmosphereData.add(new AtmosphereDataEntry(345000, 1464.75,
				2.271676e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(346000, 1465.33, 2.23328e-11));
		atmosphereData.add(new AtmosphereDataEntry(347000, 1465.91,
				2.195608e-11));
		atmosphereData.add(new AtmosphereDataEntry(348000, 1466.47,
				2.158643e-11));
		atmosphereData.add(new AtmosphereDataEntry(349000, 1467.04,
				2.122372e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(350000, 1467.59, 2.08678e-11));
		atmosphereData.add(new AtmosphereDataEntry(351000, 1468.14,
				2.051853e-11));
		atmosphereData.add(new AtmosphereDataEntry(352000, 1468.69,
				2.017578e-11));
		atmosphereData.add(new AtmosphereDataEntry(353000, 1469.22,
				1.983941e-11));
		atmosphereData.add(new AtmosphereDataEntry(354000, 1469.75,
				1.950929e-11));
		atmosphereData.add(new AtmosphereDataEntry(355000, 1470.28,
				1.918529e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(356000, 1470.8, 1.88673e-11));
		atmosphereData.add(new AtmosphereDataEntry(357000, 1471.31,
				1.855518e-11));
		atmosphereData.add(new AtmosphereDataEntry(358000, 1471.82,
				1.824882e-11));
		atmosphereData.add(new AtmosphereDataEntry(359000, 1472.32,
				1.794809e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(360000, 1472.81, 1.76529e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(361000, 1473.3, 1.736312e-11));
		atmosphereData.add(new AtmosphereDataEntry(362000, 1473.78,
				1.707864e-11));
		atmosphereData.add(new AtmosphereDataEntry(363000, 1474.25,
				1.679937e-11));
		atmosphereData.add(new AtmosphereDataEntry(364000, 1474.72,
				1.652518e-11));
		atmosphereData.add(new AtmosphereDataEntry(365000, 1475.19,
				1.625599e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(366000, 1475.64, 1.59917e-11));
		atmosphereData.add(new AtmosphereDataEntry(367000, 1476.09,
				1.573219e-11));
		atmosphereData.add(new AtmosphereDataEntry(368000, 1476.54,
				1.547739e-11));
		atmosphereData.add(new AtmosphereDataEntry(369000, 1476.97,
				1.522719e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(370000, 1477.41, 1.49815e-11));
		atmosphereData.add(new AtmosphereDataEntry(371000, 1477.83,
				1.474024e-11));
		atmosphereData.add(new AtmosphereDataEntry(372000, 1478.25,
				1.450332e-11));
		atmosphereData.add(new AtmosphereDataEntry(373000, 1478.67,
				1.427064e-11));
		atmosphereData.add(new AtmosphereDataEntry(374000, 1479.07,
				1.404213e-11));
		atmosphereData.add(new AtmosphereDataEntry(375000, 1479.47,
				1.381771e-11));
		atmosphereData.add(new AtmosphereDataEntry(376000, 1479.87,
				1.359729e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(377000, 1480.26, 1.33808e-11));
		atmosphereData.add(new AtmosphereDataEntry(378000, 1480.64,
				1.316816e-11));
		atmosphereData.add(new AtmosphereDataEntry(379000, 1481.01,
				1.295929e-11));
		atmosphereData.add(new AtmosphereDataEntry(380000, 1481.38,
				1.275412e-11));
		atmosphereData.add(new AtmosphereDataEntry(381000, 1481.75,
				1.255257e-11));
		atmosphereData.add(new AtmosphereDataEntry(382000, 1482.11,
				1.235459e-11));
		atmosphereData.add(new AtmosphereDataEntry(383000, 1482.46,
				1.216009e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(384000, 1482.8, 1.196901e-11));
		atmosphereData.add(new AtmosphereDataEntry(385000, 1483.14,
				1.178129e-11));
		atmosphereData.add(new AtmosphereDataEntry(386000, 1483.47,
				1.159686e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(387000, 1483.8, 1.141565e-11));
		atmosphereData
				.add(new AtmosphereDataEntry(388000, 1484.12, 1.12376e-11));
		atmosphereData.add(new AtmosphereDataEntry(389000, 1484.43,
				1.106266e-11));
		atmosphereData.add(new AtmosphereDataEntry(390000, 1484.74,
				1.089077e-11));
		atmosphereData.add(new AtmosphereDataEntry(391000, 1485.04,
				1.072185e-11));
		atmosphereData.add(new AtmosphereDataEntry(392000, 1485.34,
				1.055587e-11));
		atmosphereData.add(new AtmosphereDataEntry(393000, 1485.63,
				1.039276e-11));
		atmosphereData.add(new AtmosphereDataEntry(394000, 1485.91,
				1.023246e-11));
		atmosphereData.add(new AtmosphereDataEntry(395000, 1486.19,
				1.007493e-11));
		atmosphereData.add(new AtmosphereDataEntry(396000, 1486.46,
				9.920115e-12));
		atmosphereData.add(new AtmosphereDataEntry(397000, 1486.72,
				9.767956e-12));
		atmosphereData.add(new AtmosphereDataEntry(398000, 1486.98,
				9.618407e-12));
		atmosphereData.add(new AtmosphereDataEntry(399000, 1487.23,
				9.471418e-12));
		atmosphereData.add(new AtmosphereDataEntry(400000, 1487.48,
				9.326942e-12));
		atmosphereData.add(new AtmosphereDataEntry(401000, 1487.78,
				9.187881e-12));
		atmosphereData.add(new AtmosphereDataEntry(402000, 1488.07,
				9.051103e-12));
		atmosphereData.add(new AtmosphereDataEntry(403000, 1488.36,
				8.916565e-12));
		atmosphereData.add(new AtmosphereDataEntry(404000, 1488.64,
				8.784227e-12));
		atmosphereData.add(new AtmosphereDataEntry(405000, 1488.92,
				8.654052e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(406000, 1489.2, 8.525999e-12));
		atmosphereData.add(new AtmosphereDataEntry(407000, 1489.48,
				8.400033e-12));
		atmosphereData.add(new AtmosphereDataEntry(408000, 1489.75,
				8.276115e-12));
		atmosphereData.add(new AtmosphereDataEntry(409000, 1490.02,
				8.154209e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(410000, 1490.28, 8.03428e-12));
		atmosphereData.add(new AtmosphereDataEntry(411000, 1490.54,
				7.916294e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(412000, 1490.8, 7.800215e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(413000, 1491.05, 7.68601e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(414000, 1491.3, 7.573647e-12));
		atmosphereData.add(new AtmosphereDataEntry(415000, 1491.55,
				7.463092e-12));
		atmosphereData.add(new AtmosphereDataEntry(416000, 1491.79,
				7.354315e-12));
		atmosphereData.add(new AtmosphereDataEntry(417000, 1492.03,
				7.247285e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(418000, 1492.26, 7.14197e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(419000, 1492.49, 7.03834e-12));
		atmosphereData.add(new AtmosphereDataEntry(420000, 1492.72,
				6.936368e-12));
		atmosphereData.add(new AtmosphereDataEntry(421000, 1492.95,
				6.836023e-12));
		atmosphereData.add(new AtmosphereDataEntry(422000, 1493.17,
				6.737277e-12));
		atmosphereData.add(new AtmosphereDataEntry(423000, 1493.38,
				6.640103e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(424000, 1493.6, 6.544473e-12));
		atmosphereData.add(new AtmosphereDataEntry(425000, 1493.81,
				6.450361e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(426000, 1494.01, 6.35774e-12));
		atmosphereData.add(new AtmosphereDataEntry(427000, 1494.22,
				6.266586e-12));
		atmosphereData.add(new AtmosphereDataEntry(428000, 1494.42,
				6.176871e-12));
		atmosphereData.add(new AtmosphereDataEntry(429000, 1494.61,
				6.088573e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(430000, 1494.8, 6.001666e-12));
		atmosphereData.add(new AtmosphereDataEntry(431000, 1494.99,
				5.916126e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(432000, 1495.18, 5.83193e-12));
		atmosphereData.add(new AtmosphereDataEntry(433000, 1495.36,
				5.749056e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(434000, 1495.54, 5.66748e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(435000, 1495.71, 5.58718e-12));
		atmosphereData.add(new AtmosphereDataEntry(436000, 1495.88,
				5.508135e-12));
		atmosphereData.add(new AtmosphereDataEntry(437000, 1496.05,
				5.430324e-12));
		atmosphereData.add(new AtmosphereDataEntry(438000, 1496.21,
				5.353724e-12));
		atmosphereData.add(new AtmosphereDataEntry(439000, 1496.37,
				5.278317e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(440000, 1496.53, 5.20408e-12));
		atmosphereData.add(new AtmosphereDataEntry(441000, 1496.68,
				5.130996e-12));
		atmosphereData.add(new AtmosphereDataEntry(442000, 1496.83,
				5.059044e-12));
		atmosphereData.add(new AtmosphereDataEntry(443000, 1496.97,
				4.988205e-12));
		atmosphereData.add(new AtmosphereDataEntry(444000, 1497.12,
				4.918461e-12));
		atmosphereData.add(new AtmosphereDataEntry(445000, 1497.25,
				4.849792e-12));
		atmosphereData.add(new AtmosphereDataEntry(446000, 1497.39,
				4.782181e-12));
		atmosphereData.add(new AtmosphereDataEntry(447000, 1497.52,
				4.715611e-12));
		atmosphereData.add(new AtmosphereDataEntry(448000, 1497.65,
				4.650063e-12));
		atmosphereData.add(new AtmosphereDataEntry(449000, 1497.77,
				4.585521e-12));
		atmosphereData.add(new AtmosphereDataEntry(450000, 1497.89,
				4.521968e-12));
		atmosphereData.add(new AtmosphereDataEntry(451000, 1498.01,
				4.459386e-12));
		atmosphereData.add(new AtmosphereDataEntry(452000, 1498.12,
				4.397761e-12));
		atmosphereData.add(new AtmosphereDataEntry(453000, 1498.23,
				4.337076e-12));
		atmosphereData.add(new AtmosphereDataEntry(454000, 1498.34,
				4.277316e-12));
		atmosphereData.add(new AtmosphereDataEntry(455000, 1498.44,
				4.218465e-12));
		atmosphereData.add(new AtmosphereDataEntry(456000, 1498.54,
				4.160508e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(457000, 1498.63, 4.10343e-12));
		atmosphereData.add(new AtmosphereDataEntry(458000, 1498.72,
				4.047217e-12));
		atmosphereData.add(new AtmosphereDataEntry(459000, 1498.81,
				3.991854e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(460000, 1498.9, 3.937328e-12));
		atmosphereData.add(new AtmosphereDataEntry(461000, 1498.98,
				3.883624e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(462000, 1499.05, 3.83073e-12));
		atmosphereData.add(new AtmosphereDataEntry(463000, 1499.13,
				3.778631e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(464000, 1499.2, 3.727315e-12));
		atmosphereData.add(new AtmosphereDataEntry(465000, 1499.26,
				3.676769e-12));
		atmosphereData.add(new AtmosphereDataEntry(466000, 1499.33,
				3.626981e-12));
		atmosphereData.add(new AtmosphereDataEntry(467000, 1499.39,
				3.577937e-12));
		atmosphereData.add(new AtmosphereDataEntry(468000, 1499.44,
				3.529626e-12));
		atmosphereData.add(new AtmosphereDataEntry(469000, 1499.49,
				3.482036e-12));
		atmosphereData.add(new AtmosphereDataEntry(470000, 1499.54,
				3.435156e-12));
		atmosphereData.add(new AtmosphereDataEntry(471000, 1499.59,
				3.388973e-12));
		atmosphereData.add(new AtmosphereDataEntry(472000, 1499.63,
				3.343476e-12));
		atmosphereData.add(new AtmosphereDataEntry(473000, 1499.67,
				3.298654e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(474000, 1499.7, 3.254497e-12));
		atmosphereData.add(new AtmosphereDataEntry(475000, 1499.73,
				3.210993e-12));
		atmosphereData.add(new AtmosphereDataEntry(476000, 1499.76,
				3.168132e-12));
		atmosphereData.add(new AtmosphereDataEntry(477000, 1499.78,
				3.125904e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(478000, 1499.8, 3.084298e-12));
		atmosphereData.add(new AtmosphereDataEntry(479000, 1499.82,
				3.043305e-12));
		atmosphereData.add(new AtmosphereDataEntry(480000, 1499.83,
				3.002914e-12));
		atmosphereData.add(new AtmosphereDataEntry(481000, 1499.84,
				2.963116e-12));
		atmosphereData.add(new AtmosphereDataEntry(482000, 1499.84,
				2.923901e-12));
		atmosphereData.add(new AtmosphereDataEntry(483000, 1499.84,
				2.885261e-12));
		atmosphereData.add(new AtmosphereDataEntry(484000, 1499.84,
				2.847185e-12));
		atmosphereData.add(new AtmosphereDataEntry(485000, 1499.84,
				2.809665e-12));
		atmosphereData.add(new AtmosphereDataEntry(486000, 1499.83,
				2.772693e-12));
		atmosphereData.add(new AtmosphereDataEntry(487000, 1499.82,
				2.736258e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(488000, 1499.8, 2.700354e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(489000, 1499.78, 2.66497e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(490000, 1499.76, 2.6301e-12));
		atmosphereData.add(new AtmosphereDataEntry(491000, 1499.73,
				2.595735e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(492000, 1499.7, 2.561867e-12));
		atmosphereData.add(new AtmosphereDataEntry(493000, 1499.66,
				2.528488e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(494000, 1499.63, 2.49559e-12));
		atmosphereData.add(new AtmosphereDataEntry(495000, 1499.59,
				2.463166e-12));
		atmosphereData.add(new AtmosphereDataEntry(496000, 1499.54,
				2.431209e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(497000, 1499.49, 2.39971e-12));
		atmosphereData.add(new AtmosphereDataEntry(498000, 1499.44,
				2.368664e-12));
		atmosphereData.add(new AtmosphereDataEntry(499000, 1499.38,
				2.338062e-12));
		atmosphereData.add(new AtmosphereDataEntry(500000, 1499.33,
				2.307898e-12));
		atmosphereData.add(new AtmosphereDataEntry(501000, 1499.46,
				2.279007e-12));
		atmosphereData.add(new AtmosphereDataEntry(502000, 1499.59,
				2.250507e-12));
		atmosphereData.add(new AtmosphereDataEntry(503000, 1499.72,
				2.222393e-12));
		atmosphereData.add(new AtmosphereDataEntry(504000, 1499.85,
				2.194659e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(505000, 1499.98, 2.1673e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(506000, 1500.1, 2.14031e-12));
		atmosphereData.add(new AtmosphereDataEntry(507000, 1500.23,
				2.113684e-12));
		atmosphereData.add(new AtmosphereDataEntry(508000, 1500.35,
				2.087416e-12));
		atmosphereData.add(new AtmosphereDataEntry(509000, 1500.48,
				2.061503e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(510000, 1500.6, 2.035937e-12));
		atmosphereData.add(new AtmosphereDataEntry(511000, 1500.72,
				2.010715e-12));
		atmosphereData.add(new AtmosphereDataEntry(512000, 1500.84,
				1.985832e-12));
		atmosphereData.add(new AtmosphereDataEntry(513000, 1500.95,
				1.961281e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(514000, 1501.07, 1.93706e-12));
		atmosphereData.add(new AtmosphereDataEntry(515000, 1501.18,
				1.913163e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(516000, 1501.3, 1.889585e-12));
		atmosphereData.add(new AtmosphereDataEntry(517000, 1501.41,
				1.866322e-12));
		atmosphereData.add(new AtmosphereDataEntry(518000, 1501.52,
				1.843369e-12));
		atmosphereData.add(new AtmosphereDataEntry(519000, 1501.63,
				1.820722e-12));
		atmosphereData.add(new AtmosphereDataEntry(520000, 1501.74,
				1.798377e-12));
		atmosphereData.add(new AtmosphereDataEntry(521000, 1501.85,
				1.776329e-12));
		atmosphereData.add(new AtmosphereDataEntry(522000, 1501.95,
				1.754573e-12));
		atmosphereData.add(new AtmosphereDataEntry(523000, 1502.06,
				1.733107e-12));
		atmosphereData.add(new AtmosphereDataEntry(524000, 1502.16,
				1.711925e-12));
		atmosphereData.add(new AtmosphereDataEntry(525000, 1502.26,
				1.691024e-12));
		atmosphereData.add(new AtmosphereDataEntry(526000, 1502.36,
				1.670399e-12));
		atmosphereData.add(new AtmosphereDataEntry(527000, 1502.46,
				1.650047e-12));
		atmosphereData.add(new AtmosphereDataEntry(528000, 1502.56,
				1.629964e-12));
		atmosphereData.add(new AtmosphereDataEntry(529000, 1502.66,
				1.610146e-12));
		atmosphereData.add(new AtmosphereDataEntry(530000, 1502.75,
				1.590589e-12));
		atmosphereData.add(new AtmosphereDataEntry(531000, 1502.85,
				1.571289e-12));
		atmosphereData.add(new AtmosphereDataEntry(532000, 1502.94,
				1.552244e-12));
		atmosphereData.add(new AtmosphereDataEntry(533000, 1503.03,
				1.533449e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(534000, 1503.12, 1.5149e-12));
		atmosphereData.add(new AtmosphereDataEntry(535000, 1503.21,
				1.496595e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(536000, 1503.3, 1.47853e-12));
		atmosphereData.add(new AtmosphereDataEntry(537000, 1503.39,
				1.460701e-12));
		atmosphereData.add(new AtmosphereDataEntry(538000, 1503.47,
				1.443106e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(539000, 1503.56, 1.42574e-12));
		atmosphereData.add(new AtmosphereDataEntry(540000, 1503.64,
				1.408601e-12));
		atmosphereData.add(new AtmosphereDataEntry(541000, 1503.72,
				1.391686e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(542000, 1503.8, 1.374991e-12));
		atmosphereData.add(new AtmosphereDataEntry(543000, 1503.88,
				1.358513e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(544000, 1503.96, 1.34225e-12));
		atmosphereData.add(new AtmosphereDataEntry(545000, 1504.03,
				1.326198e-12));
		atmosphereData.add(new AtmosphereDataEntry(546000, 1504.11,
				1.310354e-12));
		atmosphereData.add(new AtmosphereDataEntry(547000, 1504.18,
				1.294716e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(548000, 1504.25, 1.27928e-12));
		atmosphereData.add(new AtmosphereDataEntry(549000, 1504.32,
				1.264044e-12));
		atmosphereData.add(new AtmosphereDataEntry(550000, 1504.39,
				1.249004e-12));
		atmosphereData.add(new AtmosphereDataEntry(551000, 1504.46,
				1.234159e-12));
		atmosphereData.add(new AtmosphereDataEntry(552000, 1504.53,
				1.219506e-12));
		atmosphereData.add(new AtmosphereDataEntry(553000, 1504.59,
				1.205042e-12));
		atmosphereData.add(new AtmosphereDataEntry(554000, 1504.66,
				1.190763e-12));
		atmosphereData.add(new AtmosphereDataEntry(555000, 1504.72,
				1.176669e-12));
		atmosphereData.add(new AtmosphereDataEntry(556000, 1504.78,
				1.162755e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(557000, 1504.85, 1.14902e-12));
		atmosphereData
				.add(new AtmosphereDataEntry(558000, 1504.9, 1.135462e-12));
		atmosphereData.add(new AtmosphereDataEntry(559000, 1504.96,
				1.122077e-12));
		atmosphereData.add(new AtmosphereDataEntry(560000, 1505.02,
				1.108863e-12));
		atmosphereData.add(new AtmosphereDataEntry(561000, 1505.08,
				1.095819e-12));
		atmosphereData.add(new AtmosphereDataEntry(562000, 1505.13,
				1.082941e-12));
		atmosphereData.add(new AtmosphereDataEntry(563000, 1505.18,
				1.070227e-12));
		atmosphereData.add(new AtmosphereDataEntry(564000, 1505.23,
				1.057676e-12));
		atmosphereData.add(new AtmosphereDataEntry(565000, 1505.28,
				1.045285e-12));
		atmosphereData.add(new AtmosphereDataEntry(566000, 1505.33,
				1.033051e-12));
		atmosphereData.add(new AtmosphereDataEntry(567000, 1505.38,
				1.020973e-12));
		atmosphereData.add(new AtmosphereDataEntry(568000, 1505.43,
				1.009048e-12));
		atmosphereData.add(new AtmosphereDataEntry(569000, 1505.47,
				9.972748e-13));
		atmosphereData.add(new AtmosphereDataEntry(570000, 1505.52,
				9.856507e-13));
		atmosphereData.add(new AtmosphereDataEntry(571000, 1505.56,
				9.741738e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(572000, 1505.6, 9.628422e-13));
		atmosphereData.add(new AtmosphereDataEntry(573000, 1505.64,
				9.516539e-13));
		atmosphereData.add(new AtmosphereDataEntry(574000, 1505.68,
				9.406068e-13));
		atmosphereData.add(new AtmosphereDataEntry(575000, 1505.72,
				9.296992e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(576000, 1505.75, 9.18929e-13));
		atmosphereData.add(new AtmosphereDataEntry(577000, 1505.79,
				9.082945e-13));
		atmosphereData.add(new AtmosphereDataEntry(578000, 1505.82,
				8.977937e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(579000, 1505.85, 8.87425e-13));
		atmosphereData.add(new AtmosphereDataEntry(580000, 1505.88,
				8.771865e-13));
		atmosphereData.add(new AtmosphereDataEntry(581000, 1505.91,
				8.670764e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(582000, 1505.94, 8.57093e-13));
		atmosphereData.add(new AtmosphereDataEntry(583000, 1505.97,
				8.472347e-13));
		atmosphereData.add(new AtmosphereDataEntry(584000, 1506, 8.374996e-13));
		atmosphereData.add(new AtmosphereDataEntry(585000, 1506.02,
				8.278862e-13));
		atmosphereData.add(new AtmosphereDataEntry(586000, 1506.04,
				8.183929e-13));
		atmosphereData.add(new AtmosphereDataEntry(587000, 1506.07,
				8.090179e-13));
		atmosphereData.add(new AtmosphereDataEntry(588000, 1506.09,
				7.997598e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(589000, 1506.11, 7.90617e-13));
		atmosphereData.add(new AtmosphereDataEntry(590000, 1506.12,
				7.815878e-13));
		atmosphereData.add(new AtmosphereDataEntry(591000, 1506.14,
				7.726709e-13));
		atmosphereData.add(new AtmosphereDataEntry(592000, 1506.16,
				7.638646e-13));
		atmosphereData.add(new AtmosphereDataEntry(593000, 1506.17,
				7.551675e-13));
		atmosphereData.add(new AtmosphereDataEntry(594000, 1506.18,
				7.465782e-13));
		atmosphereData.add(new AtmosphereDataEntry(595000, 1506.19,
				7.380953e-13));
		atmosphereData.add(new AtmosphereDataEntry(596000, 1506.21,
				7.297172e-13));
		atmosphereData.add(new AtmosphereDataEntry(597000, 1506.21,
				7.214426e-13));
		atmosphereData.add(new AtmosphereDataEntry(598000, 1506.22,
				7.132702e-13));
		atmosphereData.add(new AtmosphereDataEntry(599000, 1506.23,
				7.051985e-13));
		atmosphereData.add(new AtmosphereDataEntry(600000, 1506.23,
				6.972263e-13));
		atmosphereData.add(new AtmosphereDataEntry(601000, 1506.27,
				6.895109e-13));
		atmosphereData.add(new AtmosphereDataEntry(602000, 1506.31,
				6.818869e-13));
		atmosphereData.add(new AtmosphereDataEntry(603000, 1506.35,
				6.743532e-13));
		atmosphereData.add(new AtmosphereDataEntry(604000, 1506.39,
				6.669087e-13));
		atmosphereData.add(new AtmosphereDataEntry(605000, 1506.43,
				6.595521e-13));
		atmosphereData.add(new AtmosphereDataEntry(606000, 1506.47,
				6.522825e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(607000, 1506.5, 6.450986e-13));
		atmosphereData.add(new AtmosphereDataEntry(608000, 1506.54,
				6.379995e-13));
		atmosphereData.add(new AtmosphereDataEntry(609000, 1506.58,
				6.309841e-13));
		atmosphereData.add(new AtmosphereDataEntry(610000, 1506.61,
				6.240513e-13));
		atmosphereData.add(new AtmosphereDataEntry(611000, 1506.65,
				6.172001e-13));
		atmosphereData.add(new AtmosphereDataEntry(612000, 1506.68,
				6.104295e-13));
		atmosphereData.add(new AtmosphereDataEntry(613000, 1506.71,
				6.037384e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(614000, 1506.75, 5.97126e-13));
		atmosphereData.add(new AtmosphereDataEntry(615000, 1506.78,
				5.905911e-13));
		atmosphereData.add(new AtmosphereDataEntry(616000, 1506.81,
				5.841328e-13));
		atmosphereData.add(new AtmosphereDataEntry(617000, 1506.84,
				5.777503e-13));
		atmosphereData.add(new AtmosphereDataEntry(618000, 1506.88,
				5.714424e-13));
		atmosphereData.add(new AtmosphereDataEntry(619000, 1506.91,
				5.652084e-13));
		atmosphereData.add(new AtmosphereDataEntry(620000, 1506.94,
				5.590472e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(621000, 1506.97, 5.52958e-13));
		atmosphereData.add(new AtmosphereDataEntry(622000, 1507, 5.469399e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(623000, 1507.03, 5.40992e-13));
		atmosphereData.add(new AtmosphereDataEntry(624000, 1507.05,
				5.351135e-13));
		atmosphereData.add(new AtmosphereDataEntry(625000, 1507.08,
				5.293034e-13));
		atmosphereData.add(new AtmosphereDataEntry(626000, 1507.11,
				5.235609e-13));
		atmosphereData.add(new AtmosphereDataEntry(627000, 1507.14,
				5.178853e-13));
		atmosphereData.add(new AtmosphereDataEntry(628000, 1507.16,
				5.122756e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(629000, 1507.19, 5.06731e-13));
		atmosphereData.add(new AtmosphereDataEntry(630000, 1507.21,
				5.012508e-13));
		atmosphereData.add(new AtmosphereDataEntry(631000, 1507.24,
				4.958342e-13));
		atmosphereData.add(new AtmosphereDataEntry(632000, 1507.26,
				4.904803e-13));
		atmosphereData.add(new AtmosphereDataEntry(633000, 1507.29,
				4.851884e-13));
		atmosphereData.add(new AtmosphereDataEntry(634000, 1507.31,
				4.799577e-13));
		atmosphereData.add(new AtmosphereDataEntry(635000, 1507.33,
				4.747876e-13));
		atmosphereData.add(new AtmosphereDataEntry(636000, 1507.35,
				4.696771e-13));
		atmosphereData.add(new AtmosphereDataEntry(637000, 1507.38,
				4.646257e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(638000, 1507.4, 4.596325e-13));
		atmosphereData.add(new AtmosphereDataEntry(639000, 1507.42,
				4.546969e-13));
		atmosphereData.add(new AtmosphereDataEntry(640000, 1507.44,
				4.498182e-13));
		atmosphereData.add(new AtmosphereDataEntry(641000, 1507.46,
				4.449956e-13));
		atmosphereData.add(new AtmosphereDataEntry(642000, 1507.48,
				4.402285e-13));
		atmosphereData.add(new AtmosphereDataEntry(643000, 1507.49,
				4.355161e-13));
		atmosphereData.add(new AtmosphereDataEntry(644000, 1507.51,
				4.308579e-13));
		atmosphereData.add(new AtmosphereDataEntry(645000, 1507.53,
				4.262532e-13));
		atmosphereData.add(new AtmosphereDataEntry(646000, 1507.55,
				4.217013e-13));
		atmosphereData.add(new AtmosphereDataEntry(647000, 1507.56,
				4.172015e-13));
		atmosphereData.add(new AtmosphereDataEntry(648000, 1507.58,
				4.127533e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(649000, 1507.6, 4.08356e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(650000, 1507.61, 4.04009e-13));
		atmosphereData.add(new AtmosphereDataEntry(651000, 1507.63,
				3.997116e-13));
		atmosphereData.add(new AtmosphereDataEntry(652000, 1507.64,
				3.954633e-13));
		atmosphereData.add(new AtmosphereDataEntry(653000, 1507.65,
				3.912635e-13));
		atmosphereData.add(new AtmosphereDataEntry(654000, 1507.67,
				3.871115e-13));
		atmosphereData.add(new AtmosphereDataEntry(655000, 1507.68,
				3.830069e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(656000, 1507.69, 3.78949e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(657000, 1507.7, 3.749372e-13));
		atmosphereData.add(new AtmosphereDataEntry(658000, 1507.71,
				3.709711e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(659000, 1507.72, 3.6705e-13));
		atmosphereData.add(new AtmosphereDataEntry(660000, 1507.73,
				3.631734e-13));
		atmosphereData.add(new AtmosphereDataEntry(661000, 1507.74,
				3.593408e-13));
		atmosphereData.add(new AtmosphereDataEntry(662000, 1507.75,
				3.555516e-13));
		atmosphereData.add(new AtmosphereDataEntry(663000, 1507.76,
				3.518054e-13));
		atmosphereData.add(new AtmosphereDataEntry(664000, 1507.77,
				3.481015e-13));
		atmosphereData.add(new AtmosphereDataEntry(665000, 1507.78,
				3.444396e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(666000, 1507.78, 3.40819e-13));
		atmosphereData.add(new AtmosphereDataEntry(667000, 1507.79,
				3.372393e-13));
		atmosphereData.add(new AtmosphereDataEntry(668000, 1507.8, 3.337e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(669000, 1507.8, 3.302006e-13));
		atmosphereData.add(new AtmosphereDataEntry(670000, 1507.81,
				3.267406e-13));
		atmosphereData.add(new AtmosphereDataEntry(671000, 1507.81,
				3.233196e-13));
		atmosphereData.add(new AtmosphereDataEntry(672000, 1507.81,
				3.199371e-13));
		atmosphereData.add(new AtmosphereDataEntry(673000, 1507.82,
				3.165926e-13));
		atmosphereData.add(new AtmosphereDataEntry(674000, 1507.82,
				3.132857e-13));
		atmosphereData.add(new AtmosphereDataEntry(675000, 1507.82,
				3.100159e-13));
		atmosphereData.add(new AtmosphereDataEntry(676000, 1507.82,
				3.067828e-13));
		atmosphereData.add(new AtmosphereDataEntry(677000, 1507.83,
				3.035859e-13));
		atmosphereData.add(new AtmosphereDataEntry(678000, 1507.83,
				3.004249e-13));
		atmosphereData.add(new AtmosphereDataEntry(679000, 1507.83,
				2.972992e-13));
		atmosphereData.add(new AtmosphereDataEntry(680000, 1507.83,
				2.942085e-13));
		atmosphereData.add(new AtmosphereDataEntry(681000, 1507.83,
				2.911523e-13));
		atmosphereData.add(new AtmosphereDataEntry(682000, 1507.83,
				2.881303e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(683000, 1507.82, 2.85142e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(684000, 1507.82, 2.82187e-13));
		atmosphereData.add(new AtmosphereDataEntry(685000, 1507.82,
				2.792649e-13));
		atmosphereData.add(new AtmosphereDataEntry(686000, 1507.82,
				2.763754e-13));
		atmosphereData.add(new AtmosphereDataEntry(687000, 1507.81,
				2.735181e-13));
		atmosphereData.add(new AtmosphereDataEntry(688000, 1507.81,
				2.706925e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(689000, 1507.8, 2.678983e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(690000, 1507.8, 2.651351e-13));
		atmosphereData.add(new AtmosphereDataEntry(691000, 1507.79,
				2.624026e-13));
		atmosphereData.add(new AtmosphereDataEntry(692000, 1507.79,
				2.597004e-13));
		atmosphereData.add(new AtmosphereDataEntry(693000, 1507.78,
				2.570281e-13));
		atmosphereData.add(new AtmosphereDataEntry(694000, 1507.77,
				2.543854e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(695000, 1507.76, 2.51772e-13));
		atmosphereData.add(new AtmosphereDataEntry(696000, 1507.76,
				2.491874e-13));
		atmosphereData.add(new AtmosphereDataEntry(697000, 1507.75,
				2.466314e-13));
		atmosphereData.add(new AtmosphereDataEntry(698000, 1507.74,
				2.441036e-13));
		atmosphereData.add(new AtmosphereDataEntry(699000, 1507.73,
				2.416036e-13));
		atmosphereData.add(new AtmosphereDataEntry(700000, 1507.35,
				2.419776e-13));
		atmosphereData.add(new AtmosphereDataEntry(701000, 1507.35,
				2.395998e-13));
		atmosphereData.add(new AtmosphereDataEntry(702000, 1507.35,
				2.372464e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(703000, 1507.35, 2.34917e-13));
		atmosphereData.add(new AtmosphereDataEntry(704000, 1507.35,
				2.326114e-13));
		atmosphereData.add(new AtmosphereDataEntry(705000, 1507.35,
				2.303294e-13));
		atmosphereData.add(new AtmosphereDataEntry(706000, 1507.35,
				2.280706e-13));
		atmosphereData.add(new AtmosphereDataEntry(707000, 1507.35,
				2.258349e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(708000, 1507.35, 2.23622e-13));
		atmosphereData.add(new AtmosphereDataEntry(709000, 1507.35,
				2.214317e-13));
		atmosphereData.add(new AtmosphereDataEntry(710000, 1507.35,
				2.192636e-13));
		atmosphereData.add(new AtmosphereDataEntry(711000, 1507.35,
				2.171177e-13));
		atmosphereData.add(new AtmosphereDataEntry(712000, 1507.35,
				2.149936e-13));
		atmosphereData.add(new AtmosphereDataEntry(713000, 1507.35,
				2.128912e-13));
		atmosphereData.add(new AtmosphereDataEntry(714000, 1507.35,
				2.108101e-13));
		atmosphereData.add(new AtmosphereDataEntry(715000, 1507.35,
				2.087502e-13));
		atmosphereData.add(new AtmosphereDataEntry(716000, 1507.35,
				2.067113e-13));
		atmosphereData.add(new AtmosphereDataEntry(717000, 1507.35,
				2.046931e-13));
		atmosphereData.add(new AtmosphereDataEntry(718000, 1507.35,
				2.026954e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(719000, 1507.35, 2.00718e-13));
		atmosphereData.add(new AtmosphereDataEntry(720000, 1507.35,
				1.987606e-13));
		atmosphereData.add(new AtmosphereDataEntry(721000, 1507.35,
				1.968232e-13));
		atmosphereData.add(new AtmosphereDataEntry(722000, 1507.35,
				1.949054e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(723000, 1507.35, 1.93007e-13));
		atmosphereData.add(new AtmosphereDataEntry(724000, 1507.35,
				1.911279e-13));
		atmosphereData.add(new AtmosphereDataEntry(725000, 1507.35,
				1.892679e-13));
		atmosphereData.add(new AtmosphereDataEntry(726000, 1507.35,
				1.874267e-13));
		atmosphereData.add(new AtmosphereDataEntry(727000, 1507.35,
				1.856041e-13));
		atmosphereData.add(new AtmosphereDataEntry(728000, 1507.35, 1.838e-13));
		atmosphereData.add(new AtmosphereDataEntry(729000, 1507.35,
				1.820142e-13));
		atmosphereData.add(new AtmosphereDataEntry(730000, 1507.35,
				1.802464e-13));
		atmosphereData.add(new AtmosphereDataEntry(731000, 1507.35,
				1.784965e-13));
		atmosphereData.add(new AtmosphereDataEntry(732000, 1507.35,
				1.767643e-13));
		atmosphereData.add(new AtmosphereDataEntry(733000, 1507.35,
				1.750495e-13));
		atmosphereData.add(new AtmosphereDataEntry(734000, 1507.35,
				1.733522e-13));
		atmosphereData.add(new AtmosphereDataEntry(735000, 1507.35,
				1.716719e-13));
		atmosphereData.add(new AtmosphereDataEntry(736000, 1507.35,
				1.700086e-13));
		atmosphereData.add(new AtmosphereDataEntry(737000, 1507.35,
				1.683621e-13));
		atmosphereData.add(new AtmosphereDataEntry(738000, 1507.35,
				1.667322e-13));
		atmosphereData.add(new AtmosphereDataEntry(739000, 1507.35,
				1.651188e-13));
		atmosphereData.add(new AtmosphereDataEntry(740000, 1507.35,
				1.635216e-13));
		atmosphereData.add(new AtmosphereDataEntry(741000, 1507.35,
				1.619404e-13));
		atmosphereData.add(new AtmosphereDataEntry(742000, 1507.35,
				1.603753e-13));
		atmosphereData.add(new AtmosphereDataEntry(743000, 1507.35,
				1.588258e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(744000, 1507.35, 1.57292e-13));
		atmosphereData.add(new AtmosphereDataEntry(745000, 1507.35,
				1.557736e-13));
		atmosphereData.add(new AtmosphereDataEntry(746000, 1507.35,
				1.542705e-13));
		atmosphereData.add(new AtmosphereDataEntry(747000, 1507.35,
				1.527825e-13));
		atmosphereData.add(new AtmosphereDataEntry(748000, 1507.35,
				1.513094e-13));
		atmosphereData.add(new AtmosphereDataEntry(749000, 1507.35,
				1.498511e-13));
		atmosphereData.add(new AtmosphereDataEntry(750000, 1507.35,
				1.484075e-13));
		atmosphereData.add(new AtmosphereDataEntry(751000, 1507.35,
				1.469783e-13));
		atmosphereData.add(new AtmosphereDataEntry(752000, 1507.35,
				1.455636e-13));
		atmosphereData.add(new AtmosphereDataEntry(753000, 1507.35,
				1.441629e-13));
		atmosphereData.add(new AtmosphereDataEntry(754000, 1507.35,
				1.427764e-13));
		atmosphereData.add(new AtmosphereDataEntry(755000, 1507.35,
				1.414037e-13));
		atmosphereData.add(new AtmosphereDataEntry(756000, 1507.35,
				1.400448e-13));
		atmosphereData.add(new AtmosphereDataEntry(757000, 1507.35,
				1.386995e-13));
		atmosphereData.add(new AtmosphereDataEntry(758000, 1507.35,
				1.373677e-13));
		atmosphereData.add(new AtmosphereDataEntry(759000, 1507.35,
				1.360492e-13));
		atmosphereData.add(new AtmosphereDataEntry(760000, 1507.35,
				1.347438e-13));
		atmosphereData.add(new AtmosphereDataEntry(761000, 1507.35,
				1.334516e-13));
		atmosphereData.add(new AtmosphereDataEntry(762000, 1507.35,
				1.321722e-13));
		atmosphereData.add(new AtmosphereDataEntry(763000, 1507.35,
				1.309057e-13));
		atmosphereData.add(new AtmosphereDataEntry(764000, 1507.35,
				1.296518e-13));
		atmosphereData.add(new AtmosphereDataEntry(765000, 1507.35,
				1.284104e-13));
		atmosphereData.add(new AtmosphereDataEntry(766000, 1507.35,
				1.271814e-13));
		atmosphereData.add(new AtmosphereDataEntry(767000, 1507.35,
				1.259646e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(768000, 1507.35, 1.2476e-13));
		atmosphereData.add(new AtmosphereDataEntry(769000, 1507.35,
				1.235675e-13));
		atmosphereData.add(new AtmosphereDataEntry(770000, 1507.35,
				1.223867e-13));
		atmosphereData.add(new AtmosphereDataEntry(771000, 1507.35,
				1.212178e-13));
		atmosphereData.add(new AtmosphereDataEntry(772000, 1507.35,
				1.200605e-13));
		atmosphereData.add(new AtmosphereDataEntry(773000, 1507.35,
				1.189147e-13));
		atmosphereData.add(new AtmosphereDataEntry(774000, 1507.35,
				1.177803e-13));
		atmosphereData.add(new AtmosphereDataEntry(775000, 1507.35,
				1.166573e-13));
		atmosphereData.add(new AtmosphereDataEntry(776000, 1507.35,
				1.155453e-13));
		atmosphereData.add(new AtmosphereDataEntry(777000, 1507.35,
				1.144445e-13));
		atmosphereData.add(new AtmosphereDataEntry(778000, 1507.35,
				1.133545e-13));
		atmosphereData.add(new AtmosphereDataEntry(779000, 1507.35,
				1.122754e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(780000, 1507.35, 1.11207e-13));
		atmosphereData.add(new AtmosphereDataEntry(781000, 1507.35,
				1.101492e-13));
		atmosphereData.add(new AtmosphereDataEntry(782000, 1507.35,
				1.091019e-13));
		atmosphereData
				.add(new AtmosphereDataEntry(783000, 1507.35, 1.08065e-13));
		atmosphereData.add(new AtmosphereDataEntry(784000, 1507.35,
				1.070384e-13));
		atmosphereData.add(new AtmosphereDataEntry(785000, 1507.35,
				1.060219e-13));
		atmosphereData.add(new AtmosphereDataEntry(786000, 1507.35,
				1.050155e-13));
		atmosphereData.add(new AtmosphereDataEntry(787000, 1507.35,
				1.040191e-13));
		atmosphereData.add(new AtmosphereDataEntry(788000, 1507.35,
				1.030326e-13));
		atmosphereData.add(new AtmosphereDataEntry(789000, 1507.35,
				1.020558e-13));
		atmosphereData.add(new AtmosphereDataEntry(790000, 1507.35,
				1.010886e-13));
		atmosphereData.add(new AtmosphereDataEntry(791000, 1507.35,
				1.001311e-13));
		atmosphereData.add(new AtmosphereDataEntry(792000, 1507.35,
				9.918295e-14));
		atmosphereData.add(new AtmosphereDataEntry(793000, 1507.35,
				9.824421e-14));
		atmosphereData.add(new AtmosphereDataEntry(794000, 1507.35,
				9.731474e-14));
		atmosphereData.add(new AtmosphereDataEntry(795000, 1507.35,
				9.639445e-14));
		atmosphereData.add(new AtmosphereDataEntry(796000, 1507.35,
				9.548323e-14));
		atmosphereData.add(new AtmosphereDataEntry(797000, 1507.35,
				9.458101e-14));
		atmosphereData.add(new AtmosphereDataEntry(798000, 1507.35,
				9.368768e-14));
		atmosphereData.add(new AtmosphereDataEntry(799000, 1507.35,
				9.280316e-14));
		atmosphereData.add(new AtmosphereDataEntry(800000, 1507.35,
				9.192736e-14));
		atmosphereData.add(new AtmosphereDataEntry(801000, 1507.35,
				9.106018e-14));
		atmosphereData.add(new AtmosphereDataEntry(802000, 1507.35,
				9.020154e-14));
		atmosphereData.add(new AtmosphereDataEntry(803000, 1507.35,
				8.935135e-14));
		atmosphereData.add(new AtmosphereDataEntry(804000, 1507.35,
				8.850953e-14));
		atmosphereData.add(new AtmosphereDataEntry(805000, 1507.35,
				8.767598e-14));
		atmosphereData.add(new AtmosphereDataEntry(806000, 1507.35,
				8.685063e-14));
		atmosphereData.add(new AtmosphereDataEntry(807000, 1507.35,
				8.603339e-14));
		atmosphereData.add(new AtmosphereDataEntry(808000, 1507.35,
				8.522418e-14));
		atmosphereData.add(new AtmosphereDataEntry(809000, 1507.35,
				8.442291e-14));
		atmosphereData.add(new AtmosphereDataEntry(810000, 1507.35,
				8.362951e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(811000, 1507.35, 8.28439e-14));
		atmosphereData.add(new AtmosphereDataEntry(812000, 1507.35,
				8.206599e-14));
		atmosphereData.add(new AtmosphereDataEntry(813000, 1507.35,
				8.129571e-14));
		atmosphereData.add(new AtmosphereDataEntry(814000, 1507.35,
				8.053298e-14));
		atmosphereData.add(new AtmosphereDataEntry(815000, 1507.35,
				7.977772e-14));
		atmosphereData.add(new AtmosphereDataEntry(816000, 1507.35,
				7.902985e-14));
		atmosphereData.add(new AtmosphereDataEntry(817000, 1507.35,
				7.828931e-14));
		atmosphereData.add(new AtmosphereDataEntry(818000, 1507.35,
				7.755602e-14));
		atmosphereData.add(new AtmosphereDataEntry(819000, 1507.35,
				7.682989e-14));
		atmosphereData.add(new AtmosphereDataEntry(820000, 1507.35,
				7.611087e-14));
		atmosphereData.add(new AtmosphereDataEntry(821000, 1507.35,
				7.539888e-14));
		atmosphereData.add(new AtmosphereDataEntry(822000, 1507.35,
				7.469384e-14));
		atmosphereData.add(new AtmosphereDataEntry(823000, 1507.35,
				7.399569e-14));
		atmosphereData.add(new AtmosphereDataEntry(824000, 1507.35,
				7.330436e-14));
		atmosphereData.add(new AtmosphereDataEntry(825000, 1507.35,
				7.261977e-14));
		atmosphereData.add(new AtmosphereDataEntry(826000, 1507.35,
				7.194187e-14));
		atmosphereData.add(new AtmosphereDataEntry(827000, 1507.35,
				7.127057e-14));
		atmosphereData.add(new AtmosphereDataEntry(828000, 1507.35,
				7.060582e-14));
		atmosphereData.add(new AtmosphereDataEntry(829000, 1507.35,
				6.994754e-14));
		atmosphereData.add(new AtmosphereDataEntry(830000, 1507.35,
				6.929568e-14));
		atmosphereData.add(new AtmosphereDataEntry(831000, 1507.35,
				6.865016e-14));
		atmosphereData.add(new AtmosphereDataEntry(832000, 1507.35,
				6.801093e-14));
		atmosphereData.add(new AtmosphereDataEntry(833000, 1507.35,
				6.737792e-14));
		atmosphereData.add(new AtmosphereDataEntry(834000, 1507.35,
				6.675106e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(835000, 1507.35, 6.61303e-14));
		atmosphereData.add(new AtmosphereDataEntry(836000, 1507.35,
				6.551557e-14));
		atmosphereData.add(new AtmosphereDataEntry(837000, 1507.35,
				6.490682e-14));
		atmosphereData.add(new AtmosphereDataEntry(838000, 1507.35,
				6.430397e-14));
		atmosphereData.add(new AtmosphereDataEntry(839000, 1507.35,
				6.370698e-14));
		atmosphereData.add(new AtmosphereDataEntry(840000, 1507.35,
				6.311578e-14));
		atmosphereData.add(new AtmosphereDataEntry(841000, 1507.35,
				6.253031e-14));
		atmosphereData.add(new AtmosphereDataEntry(842000, 1507.35,
				6.195052e-14));
		atmosphereData.add(new AtmosphereDataEntry(843000, 1507.35,
				6.137635e-14));
		atmosphereData.add(new AtmosphereDataEntry(844000, 1507.35,
				6.080775e-14));
		atmosphereData.add(new AtmosphereDataEntry(845000, 1507.35,
				6.024465e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(846000, 1507.35, 5.9687e-14));
		atmosphereData.add(new AtmosphereDataEntry(847000, 1507.35,
				5.913475e-14));
		atmosphereData.add(new AtmosphereDataEntry(848000, 1507.35,
				5.858784e-14));
		atmosphereData.add(new AtmosphereDataEntry(849000, 1507.35,
				5.804621e-14));
		atmosphereData.add(new AtmosphereDataEntry(850000, 1507.35,
				5.750983e-14));
		atmosphereData.add(new AtmosphereDataEntry(851000, 1507.35,
				5.697862e-14));
		atmosphereData.add(new AtmosphereDataEntry(852000, 1507.35,
				5.645255e-14));
		atmosphereData.add(new AtmosphereDataEntry(853000, 1507.35,
				5.593156e-14));
		atmosphereData.add(new AtmosphereDataEntry(854000, 1507.35,
				5.541559e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(855000, 1507.35, 5.49046e-14));
		atmosphereData.add(new AtmosphereDataEntry(856000, 1507.35,
				5.439854e-14));
		atmosphereData.add(new AtmosphereDataEntry(857000, 1507.35,
				5.389736e-14));
		atmosphereData.add(new AtmosphereDataEntry(858000, 1507.35,
				5.340101e-14));
		atmosphereData.add(new AtmosphereDataEntry(859000, 1507.35,
				5.290943e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(860000, 1507.35, 5.24226e-14));
		atmosphereData.add(new AtmosphereDataEntry(861000, 1507.35,
				5.194044e-14));
		atmosphereData.add(new AtmosphereDataEntry(862000, 1507.35,
				5.146293e-14));
		atmosphereData.add(new AtmosphereDataEntry(863000, 1507.35,
				5.099001e-14));
		atmosphereData.add(new AtmosphereDataEntry(864000, 1507.35,
				5.052163e-14));
		atmosphereData.add(new AtmosphereDataEntry(865000, 1507.35,
				5.005776e-14));
		atmosphereData.add(new AtmosphereDataEntry(866000, 1507.35,
				4.959834e-14));
		atmosphereData.add(new AtmosphereDataEntry(867000, 1507.35,
				4.914333e-14));
		atmosphereData.add(new AtmosphereDataEntry(868000, 1507.35,
				4.869269e-14));
		atmosphereData.add(new AtmosphereDataEntry(869000, 1507.35,
				4.824637e-14));
		atmosphereData.add(new AtmosphereDataEntry(870000, 1507.35,
				4.780434e-14));
		atmosphereData.add(new AtmosphereDataEntry(871000, 1507.35,
				4.736654e-14));
		atmosphereData.add(new AtmosphereDataEntry(872000, 1507.35,
				4.693294e-14));
		atmosphereData.add(new AtmosphereDataEntry(873000, 1507.35,
				4.650349e-14));
		atmosphereData.add(new AtmosphereDataEntry(874000, 1507.35,
				4.607815e-14));
		atmosphereData.add(new AtmosphereDataEntry(875000, 1507.35,
				4.565689e-14));
		atmosphereData.add(new AtmosphereDataEntry(876000, 1507.35,
				4.523966e-14));
		atmosphereData.add(new AtmosphereDataEntry(877000, 1507.35,
				4.482641e-14));
		atmosphereData.add(new AtmosphereDataEntry(878000, 1507.35,
				4.441712e-14));
		atmosphereData.add(new AtmosphereDataEntry(879000, 1507.35,
				4.401174e-14));
		atmosphereData.add(new AtmosphereDataEntry(880000, 1507.35,
				4.361023e-14));
		atmosphereData.add(new AtmosphereDataEntry(881000, 1507.35,
				4.321256e-14));
		atmosphereData.add(new AtmosphereDataEntry(882000, 1507.35,
				4.281869e-14));
		atmosphereData.add(new AtmosphereDataEntry(883000, 1507.35,
				4.242857e-14));
		atmosphereData.add(new AtmosphereDataEntry(884000, 1507.35,
				4.204217e-14));
		atmosphereData.add(new AtmosphereDataEntry(885000, 1507.35,
				4.165946e-14));
		atmosphereData.add(new AtmosphereDataEntry(886000, 1507.35,
				4.128039e-14));
		atmosphereData.add(new AtmosphereDataEntry(887000, 1507.35,
				4.090494e-14));
		atmosphereData.add(new AtmosphereDataEntry(888000, 1507.35,
				4.053306e-14));
		atmosphereData.add(new AtmosphereDataEntry(889000, 1507.35,
				4.016472e-14));
		atmosphereData.add(new AtmosphereDataEntry(890000, 1507.35,
				3.979989e-14));
		atmosphereData.add(new AtmosphereDataEntry(891000, 1507.35,
				3.943853e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(892000, 1507.35, 3.90806e-14));
		atmosphereData.add(new AtmosphereDataEntry(893000, 1507.35,
				3.872608e-14));
		atmosphereData.add(new AtmosphereDataEntry(894000, 1507.35,
				3.837492e-14));
		atmosphereData
				.add(new AtmosphereDataEntry(895000, 1507.35, 3.80271e-14));
		atmosphereData.add(new AtmosphereDataEntry(896000, 1507.35,
				3.768258e-14));
		atmosphereData.add(new AtmosphereDataEntry(897000, 1507.35,
				3.734133e-14));
		atmosphereData.add(new AtmosphereDataEntry(898000, 1507.35,
				3.700332e-14));
		atmosphereData.add(new AtmosphereDataEntry(899000, 1507.35,
				3.666851e-14));
		atmosphereData.add(new AtmosphereDataEntry(900000, 1507.35,
				3.633688e-14));
	}

	@Override
	public AtmosphereData calculateOutput(double height) {
		double h = height;
		double temperatur, pressure, density, sonicSpeed;
		temperatur = pressure = density = sonicSpeed = -1.0;
		if (atmosphereData.getLast().height <= h) {
			temperatur = atmosphereData.getLast().temperature;
			density = atmosphereData.getLast().density;
		}

		// dirty hack for easy speed up. Should saves a lot of sampling in the
		// table
		// Futher improvement possible. Linked list with iterators are just not
		// the best solution

		// search for correct entries in table
		Iterator<AtmosphereDataEntry> iter = atmosphereData.iterator();
		AtmosphereDataEntry current, next;

		next = iter.next(); // set first value in list
		while (iter.hasNext()) {
			current = next; // set lower bound
			next = iter.next(); // set upper bound
			// if between lower and upper bound --> interpolate
			if (h >= current.height && h < next.height) {
				// piecewise exponential interpolation --> density [kg m^-3]
				double b = 1 / ((next.height) - (current.height))
						* Math.log(current.density / next.density);
				double a = current.density * Math.exp(b * current.height);
				density = a * Math.exp((-b) * h);
				// linear interpolation --> temperature [K]
				double distance = next.height - current.height;
				double higherFactor = (h - current.height) / distance;
				double lowerFactor = (next.height - h) / distance;
				temperatur = higherFactor * next.temperature + lowerFactor
						* current.temperature;
				break;
			}
		}
		// Pressure [Pa]
		pressure = density * R * temperatur;
		// speed of sound [m/s]
		sonicSpeed = Math.sqrt(R * kappa * temperatur);
		return new AtmosphereData(temperatur, pressure, density, sonicSpeed);
	}
}